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

    // Método para criar uma nova conta com saldo especial
    @Transactional
    public Conta criarConta(String titular, String numeroConta, BigDecimal saldoEspecial) {
        if (titular == null || titular.trim().isEmpty()) {
            throw new IllegalArgumentException("O titular não pode ser vazio ou nulo.");
        }
        if (numeroConta == null || numeroConta.trim().isEmpty()) {
            throw new IllegalArgumentException("O número da conta não pode ser vazio ou nulo.");
        }
        if (saldoEspecial.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O saldo especial não pode ser negativo.");
        }

        Conta conta = new Conta();
        conta.setTitular(titular);
        conta.setNumeroConta(numeroConta);
        conta.setSaldo(BigDecimal.ZERO); // Saldo inicial da conta é zero
        conta.setSaldoEspecial(saldoEspecial);

        return contaRepository.save(conta);
    }

    // Método para buscar uma conta pelo ID
    @Transactional(readOnly = true)
    public Conta buscarContaPorId(Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));
    }

    // Método para adicionar crédito na conta
    @Transactional
    public void creditar(Long contaId, BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do crédito deve ser maior que zero.");
        }

        Conta conta = buscarContaPorId(contaId);
        conta.setSaldo(conta.getSaldo().add(valor));
        contaRepository.save(conta);
    }

    // Método para realizar débito na conta
    @Transactional
    public void debitar(Long contaId, BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do débito deve ser maior que zero.");
        }

        Conta conta = buscarContaPorId(contaId);
        BigDecimal saldoRestante = conta.getSaldo().subtract(valor);

        if (saldoRestante.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar o saque.");
        }

        conta.setSaldo(saldoRestante);
        contaRepository.save(conta);
    }

    // Método para injeção manual do repositório (para uso em testes)
    public void setContaRepository(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }
}
