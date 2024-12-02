package com.sistema.bancario;

import com.sistema.bancario.model.Conta;
import com.sistema.bancario.repository.ContaRepository;
import com.sistema.bancario.service.ContaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContaServiceTest {

    private ContaRepository contaRepository;
    private ContaService contaService;

    @BeforeEach
    void setUp() {
        contaRepository = mock(ContaRepository.class);
        contaService = new ContaService();
        contaService.setContaRepository(contaRepository);
    }

    @Test
    void criarConta_DeveCriarContaComSaldoZeroESaldoEspecial() {
        String titular = "João Silva";
        String numeroConta = "12345678";
        BigDecimal saldoEspecial = BigDecimal.valueOf(500);

        Conta contaCriada = new Conta();
        contaCriada.setId(1L);
        contaCriada.setTitular(titular);
        contaCriada.setNumeroConta(numeroConta);
        contaCriada.setSaldo(BigDecimal.ZERO);
        contaCriada.setSaldoEspecial(saldoEspecial);

        when(contaRepository.save(any(Conta.class))).thenReturn(contaCriada);

        Conta result = contaService.criarConta(titular, numeroConta, saldoEspecial);

        assertNotNull(result);
        assertEquals(titular, result.getTitular());
        assertEquals(numeroConta, result.getNumeroConta());
        assertEquals(BigDecimal.ZERO, result.getSaldo());
        assertEquals(saldoEspecial, result.getSaldoEspecial());
        verify(contaRepository, times(1)).save(any(Conta.class));
    }

    @Test
    void creditar_DeveAdicionarValorAoSaldo() {
        Conta conta = new Conta();
        conta.setId(1L);
        conta.setSaldo(BigDecimal.valueOf(100));

        when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));

        contaService.creditar(1L, BigDecimal.valueOf(50));

        assertEquals(BigDecimal.valueOf(150), conta.getSaldo());
        verify(contaRepository, times(1)).save(conta);
    }

    @Test
    void debitar_DeveSubtrairValorDoSaldo() {
        Conta conta = new Conta();
        conta.setId(1L);
        conta.setSaldo(BigDecimal.valueOf(100));

        when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));

        contaService.debitar(1L, BigDecimal.valueOf(50));

        assertEquals(BigDecimal.valueOf(50), conta.getSaldo());
        verify(contaRepository, times(1)).save(conta);
    }

    @Test
    void debitar_DeveLancarExcecaoSeSaldoInsuficiente() {
        Conta conta = new Conta();
        conta.setId(1L);
        conta.setSaldo(BigDecimal.valueOf(50));

        when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> 
            contaService.debitar(1L, BigDecimal.valueOf(100))
        );

        assertEquals("Saldo insuficiente para realizar o saque.", exception.getMessage());
        verify(contaRepository, never()).save(any(Conta.class));
    }
}
