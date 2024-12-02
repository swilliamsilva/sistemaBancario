package com.sistema.bancario.service;

import com.sistema.bancario.model.Conta;
import com.sistema.bancario.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Transactional(readOnly = true)
    public Conta buscarContaPorId(Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
    }

    @Transactional
    public void creditar(Long idConta, BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor para crédito deve ser positivo.");
        }

        Conta conta = buscarContaPorId(idConta);
        conta.setSaldo(conta.getSaldo().add(valor));
        contaRepository.save(conta);
    }

    @Transactional
    public void debitar(Long idConta, BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor para débito deve ser positivo.");
        }

        Conta conta = buscarContaPorId(idConta);
        BigDecimal saldoRestante = conta.getSaldo().subtract(valor);

        if (saldoRestante.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("Saldo insuficiente.");
        }

        conta.setSaldo(saldoRestante);
        contaRepository.save(conta);
    }
}
