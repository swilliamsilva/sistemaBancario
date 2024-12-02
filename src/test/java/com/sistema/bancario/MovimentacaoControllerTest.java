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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MovimentacaoControllerTest {

    @InjectMocks
    private MovimentacaoController movimentacaoController;

    @Mock
    private ContaService contaService;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTransacaoTipoInvalido() {
        // Valida se uma exceção é lançada para tipo de transação inválido
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                movimentacaoController.movimentar(1L, "TRANSACAO_INVALIDA", null, model)
        );

        assertEquals("Tipo de transação inválido.", exception.getMessage());
        verify(model).addAttribute(eq("mensagemErro"), contains("Tipo de transação inválido."));
    }
}
