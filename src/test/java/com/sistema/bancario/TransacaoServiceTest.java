package com.sistema.bancario;

import com.sistema.bancario.model.Conta;
import com.sistema.bancario.model.Transacao;
import com.sistema.bancario.repository.TransacaoRepository;
import com.sistema.bancario.service.ContaService;
import com.sistema.bancario.service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransacaoServiceTest {

    @Autowired
    private TransacaoService transacaoService;

    @MockBean
    private TransacaoRepository transacaoRepository;

    @MockBean
    private ContaService contaService;

    private Conta conta;
    private Transacao transacao1;
    private Transacao transacao2;

    @BeforeEach
    public void setUp() {
        conta = new Conta();
        conta.setId(1L);
        conta.setSaldo(new BigDecimal("1000.00"));
        conta.setLimiteEspecial(new BigDecimal("500.00"));

        when(contaService.buscarContaPorId(1L)).thenReturn(conta);

        transacao1 = new Transacao();
        transacao1.setConta(conta);
        transacao1.setTipoTransacao("DEPOSITO");
        transacao1.setValor(new BigDecimal("1000.00"));
        transacao1.setDataHoraTransacao(LocalDateTime.now().minusDays(1));
        transacao1.setValorUsadoEspecial(BigDecimal.ZERO);

        transacao2 = new Transacao();
        transacao2.setConta(conta);
        transacao2.setTipoTransacao("SAQUE");
        transacao2.setValor(new BigDecimal("-500.00"));
        transacao2.setDataHoraTransacao(LocalDateTime.now());
        transacao2.setValorUsadoEspecial(BigDecimal.ZERO);

        when(transacaoRepository.findByContaIdAndTipoTransacao(1L, "DEPOSITO"))
                .thenReturn(Arrays.asList(transacao1));
        when(transacaoRepository.findByContaIdAndTipoTransacao(1L, "SAQUE"))
                .thenReturn(Arrays.asList(transacao2));
    }

    @Test
    public void testBuscarTransacoesPorTipo() {
        List<Transacao> depositos = transacaoService.buscarTransacoesPorTipo(1L, "DEPOSITO");
        assertEquals(1, depositos.size());
        assertEquals("DEPOSITO", depositos.get(0).getTipoTransacao());

        List<Transacao> saques = transacaoService.buscarTransacoesPorTipo(1L, "SAQUE");
        assertEquals(1, saques.size());
        assertEquals("SAQUE", saques.get(0).getTipoTransacao());
    }
}