package com.sistema.bancario.unit;

import com.sistema.bancario.model.Conta;
import com.sistema.bancario.model.SituacaoConta;
import com.sistema.bancario.repository.ContaRepository;
import com.sistema.bancario.service.ServicoConta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ServicoContaTests {

    @Mock
    private ContaRepository contaRepository;

    @InjectMocks
    private ServicoConta servicoConta;

    @Test
    public void testSaqueComSucesso() {
        Conta conta = new Conta();
        conta.setNumeroConta("123");
        conta.setSaldo(new BigDecimal("1000.00"));
        conta.setTitular("João Silva");
        conta.setSaldoEspecial(BigDecimal.ZERO);
        conta.setLimiteEspecial(BigDecimal.ZERO);
        conta.setSituacaoConta(SituacaoConta.ATIVA);
        
        when(contaRepository.findByNumeroConta("123")).thenReturn(Optional.of(conta));
        
        boolean resultado = servicoConta.realizarSaque("123", new BigDecimal("500.00"));
        
        assertTrue(resultado);
        assertEquals(new BigDecimal("500.00"), conta.getSaldo());
        verify(contaRepository).save(conta);
    }

    @Test
    public void testSaqueContaInativa() {
        Conta conta = new Conta();
        conta.setNumeroConta("123");
        conta.setSaldo(new BigDecimal("1000.00"));
        conta.setSituacaoConta(SituacaoConta.BLOQUEADA);
        
        when(contaRepository.findByNumeroConta("123")).thenReturn(Optional.of(conta));
        
        boolean resultado = servicoConta.realizarSaque("123", new BigDecimal("500.00"));
        
        assertFalse(resultado);
        verify(contaRepository, never()).save(any());
    }

    @Test
    public void testSaldoInsuficiente() {
        Conta conta = new Conta();
        conta.setNumeroConta("123");
        conta.setSaldo(new BigDecimal("100.00"));
        conta.setSituacaoConta(SituacaoConta.ATIVA);
        
        when(contaRepository.findByNumeroConta("123")).thenReturn(Optional.of(conta));
        
        boolean resultado = servicoConta.realizarSaque("123", new BigDecimal("500.00"));
        
        assertFalse(resultado);
        verify(contaRepository, never()).save(any());
    }

    @Test
    public void testContaNaoEncontrada() {
        when(contaRepository.findByNumeroConta("999")).thenReturn(Optional.empty());
        
        boolean resultado = servicoConta.realizarSaque("999", new BigDecimal("500.00"));
        
        assertFalse(resultado);
        verify(contaRepository, never()).save(any());
    }

    @Test
    public void testTransferenciaComSucesso() {
        Conta contaOrigem = new Conta();
        contaOrigem.setNumeroConta("123");
        contaOrigem.setSaldo(new BigDecimal("1000.00"));
        contaOrigem.setSituacaoConta(SituacaoConta.ATIVA);

        Conta contaDestino = new Conta();
        contaDestino.setNumeroConta("456");
        contaDestino.setSaldo(new BigDecimal("500.00"));
        contaDestino.setSituacaoConta(SituacaoConta.ATIVA);

        when(contaRepository.findByNumeroConta("123")).thenReturn(Optional.of(contaOrigem));
        when(contaRepository.findByNumeroConta("456")).thenReturn(Optional.of(contaDestino));

        boolean resultado = servicoConta.realizarTransferencia("123", "456", new BigDecimal("300.00"));

        assertTrue(resultado);
        assertEquals(new BigDecimal("700.00"), contaOrigem.getSaldo());
        assertEquals(new BigDecimal("800.00"), contaDestino.getSaldo());
        verify(contaRepository).save(contaOrigem);
        verify(contaRepository).save(contaDestino);
    }

    @Test
    public void testTransferenciaContaOrigemInativa() {
        Conta contaOrigem = new Conta();
        contaOrigem.setNumeroConta("123");
        contaOrigem.setSaldo(new BigDecimal("1000.00"));
        contaOrigem.setSituacaoConta(SituacaoConta.BLOQUEADA);

        Conta contaDestino = new Conta();
        contaDestino.setNumeroConta("456");
        contaDestino.setSituacaoConta(SituacaoConta.ATIVA);

        when(contaRepository.findByNumeroConta("123")).thenReturn(Optional.of(contaOrigem));
        when(contaRepository.findByNumeroConta("456")).thenReturn(Optional.of(contaDestino));

        boolean resultado = servicoConta.realizarTransferencia("123", "456", new BigDecimal("300.00"));

        assertFalse(resultado);
        verify(contaRepository, never()).save(any());
    }

    @Test
    public void testUtilizacaoChequeEspecial() {
        Conta conta = new Conta();
        conta.setNumeroConta("123");
        conta.setSaldo(new BigDecimal("100.00"));
        conta.setLimiteEspecial(new BigDecimal("1000.00"));
        conta.setSituacaoConta(SituacaoConta.ATIVA);

        when(contaRepository.findByNumeroConta("123")).thenReturn(Optional.of(conta));

        boolean resultado = servicoConta.utilizarChequeEspecial("123", new BigDecimal("500.00"));

        assertTrue(resultado);
        assertEquals(BigDecimal.ZERO, conta.getSaldo());
        assertEquals(new BigDecimal("400.00"), conta.getSaldoEspecial());
        verify(contaRepository).save(conta);
    }

    @Test
    public void testUtilizacaoChequeEspecialSemLimite() {
        Conta conta = new Conta();
        conta.setNumeroConta("123");
        conta.setSaldo(new BigDecimal("100.00"));
        conta.setLimiteEspecial(new BigDecimal("200.00"));
        conta.setSituacaoConta(SituacaoConta.ATIVA);

        when(contaRepository.findByNumeroConta("123")).thenReturn(Optional.of(conta));

        boolean resultado = servicoConta.utilizarChequeEspecial("123", new BigDecimal("500.00"));

        assertFalse(resultado);
        assertEquals(new BigDecimal("100.00"), conta.getSaldo());
        verify(contaRepository, never()).save(any());
    }

    @Test
    public void testTransferenciaValorInvalido() {
        assertThrows(IllegalArgumentException.class, () -> 
            servicoConta.realizarTransferencia("123", "456", new BigDecimal("-100.00")));
    }
} 