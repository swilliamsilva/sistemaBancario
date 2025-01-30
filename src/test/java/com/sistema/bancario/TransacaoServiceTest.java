package com.sistema.bancario;

import com.sistema.bancario.model.Conta;
import com.sistema.bancario.model.Transacao;
import com.sistema.bancario.repository.TransacaoRepository;
import com.sistema.bancario.service.ContaService;
import com.sistema.bancario.service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TransacaoServiceTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private ContaService contaService;

    @InjectMocks
    private TransacaoService transacaoService;

    private Conta conta1;
    private Conta conta2;
    private Transacao transacao1;
    private Transacao transacao2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        conta1 = new Conta();
        conta1.setId(1L);
        conta1.setNumeroConta("123");
        conta1.setSaldo(new BigDecimal("1000.00"));

        conta2 = new Conta();
        conta2.setId(2L);
        conta2.setNumeroConta("456");
        conta2.setSaldo(new BigDecimal("2000.00"));

        transacao1 = new Transacao();
        transacao1.setConta(conta1);
        transacao1.setTipoTransacao("DEPOSITO");
        transacao1.setValor(new BigDecimal("500.00"));
        transacao1.setDataHoraTransacao(LocalDateTime.now());

        transacao2 = new Transacao();
        transacao2.setConta(conta1);
        transacao2.setTipoTransacao("SAQUE");
        transacao2.setValor(new BigDecimal("300.00"));
        transacao2.setDataHoraTransacao(LocalDateTime.now());
    }

    @Test
    void testBuscarTransacoesPorTipo() {
        when(transacaoRepository.findByContaIdAndTipoTransacao(1L, "DEPOSITO"))
            .thenReturn(Arrays.asList(transacao1));

        List<Transacao> transacoes = transacaoService.buscarTransacoesPorTipo(1L, "DEPOSITO");

        assertNotNull(transacoes);
        assertEquals(1, transacoes.size());
        assertEquals("DEPOSITO", transacoes.get(0).getTipoTransacao());
    }
}