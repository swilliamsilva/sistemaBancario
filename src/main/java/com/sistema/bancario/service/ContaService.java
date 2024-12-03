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

    // Método para creditar (depositar) valor na conta
    @Transactional
    public void creditar(Long contaId, BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de depósito deve ser maior que zero");
        }

        try {
            // Recuperar a conta pelo ID
            Conta conta = buscarContaPorId(contaId);

            // Adicionar o valor ao saldo da conta
            conta.setSaldo(conta.getSaldo().add(valor));

            // Salvar a conta com o saldo atualizado no banco de dados
            contaRepository.save(conta);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao realizar o crédito na conta: " + e.getMessage());
        }
    }

    // Método para debitar (sacar) valor da conta
    @Transactional
    public void debitar(Long contaId, BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de débito deve ser maior que zero");
        }

        try {
            // Recuperar a conta pelo ID
            Conta conta = buscarContaPorId(contaId);

            // Verificar se o saldo é suficiente para o débito
            if (conta.getSaldo().compareTo(valor) < 0) {
                throw new IllegalArgumentException("Saldo insuficiente para realizar a transação");
            }

            // Subtrair o valor do saldo da conta
            conta.setSaldo(conta.getSaldo().subtract(valor));

            // Salvar a conta com o saldo atualizado no banco de dados
            contaRepository.save(conta);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao realizar o débito na conta: " + e.getMessage());
        }
    }

    // Método para buscar uma conta por ID
    @Transactional(readOnly = true)
    public Conta buscarContaPorId(Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
    }

    // Método para criar conta
    @Transactional
    public Conta criarConta(String titular, String numeroConta, BigDecimal saldoEspecial) {
        Conta conta = new Conta();
        conta.setTitular(titular);
        conta.setNumeroConta(numeroConta);
        conta.setSaldo(BigDecimal.ZERO); // Inicializa com saldo zero
        conta.setSaldoEspecial(saldoEspecial);

        return contaRepository.save(conta); // Salva a nova conta no banco de dados
    }
}
