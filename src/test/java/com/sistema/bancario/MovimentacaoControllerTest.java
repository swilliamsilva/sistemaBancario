package com.sistema.bancario;

import com.sistema.bancario.controller.MovimentacaoController;
import com.sistema.bancario.model.Conta;
import com.sistema.bancario.service.ContaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MovimentacaoControllerTest {

    @InjectMocks
    private MovimentacaoController movimentacaoController;

    @Mock
    private ContaService contaService;

    @Mock
    private Model model;

    private Conta conta;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        conta = new Conta();
        conta.setId(1L);
        conta.setSaldo(new BigDecimal("100.00"));
        conta.setSaldoEspecial(new BigDecimal("50.00"));
    }

    @Test
    public void testDeposito() {
        when(contaService.buscarContaPorId(1L)).thenReturn(conta);

        movimentacaoController.movimentar(1L, "DEPOSITO", 50.0, model);

        verify(contaService).creditar(1L, BigDecimal.valueOf(50.0));
        verify(model).addAttribute("mensagemSucesso", "Depósito realizado com sucesso!");
    }

    @Test
    public void testSaqueDentroLimite() {
        when(contaService.buscarContaPorId(1L)).thenReturn(conta);

        movimentacaoController.movimentar(1L, "SAQUE", 50.0, model);

        verify(contaService).debitar(1L, BigDecimal.valueOf(50.0));
        verify(model).addAttribute("mensagemSucesso", "Saque realizado com sucesso!");
    }

    @Test
    public void testConsultaSaldo() {
        when(contaService.buscarContaPorId(1L)).thenReturn(conta);

        movimentacaoController.movimentar(1L, "CONSULTAR_SALDO", null, model);

        verify(model).addAttribute("mensagemSucesso", "Saldo disponível: R$ " + conta.getSaldo());
    }

    @Test
    public void testTransacaoTipoInvalido() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                movimentacaoController.movimentar(1L, "TRANSACAO_INVALIDA", null, model)
        );
        assertEquals("Tipo de transação inválido.", exception.getMessage());
    }
}
