package com.sistema.bancario.service;

import com.sistema.bancario.model.Conta;
import com.sistema.bancario.model.SituacaoConta;
import com.sistema.bancario.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    // Método para creditar um valor (exemplo: depósito)
    public void creditar(Long contaId, BigDecimal valor) {
        Conta conta = buscarContaPorId(contaId);
        if (conta != null) {
            conta.setSaldo(conta.getSaldo().add(valor));
            contaRepository.save(conta);
        } else {
            throw new IllegalArgumentException("Conta não encontrada.");
        }
    }

    // Método para debitar um valor (exemplo: saque)
    public void debitar(Long contaId, BigDecimal valor) {
        Conta conta = buscarContaPorId(contaId);
        if (conta != null) {
            if (conta.getSaldo().compareTo(valor) >= 0) {
                conta.setSaldo(conta.getSaldo().subtract(valor));
                contaRepository.save(conta);
            } else {
                throw new IllegalArgumentException("Saldo insuficiente.");
            }
        } else {
            throw new IllegalArgumentException("Conta não encontrada.");
        }
    }

    // Método para buscar uma conta pelo ID
    public Conta buscarContaPorId(Long contaId) {
        Optional<Conta> conta = contaRepository.findById(contaId);
        return conta.orElse(null); // Retorna null se não encontrada
    }

    // Método para listar todas as contas
    public List<Conta> listarTodasAsContas() {
        return contaRepository.findAll();
    }

    // Método para criar uma nova conta
    public Conta criarConta(String titular, String numeroConta, BigDecimal saldoEspecial) {
        // Verifica se já existe uma conta com o mesmo número
        Optional<Conta> contaExistente = contaRepository.findByNumeroConta(numeroConta);
        if (contaExistente.isPresent()) {
            throw new IllegalArgumentException("Já existe uma conta com esse número.");
        }

        // Criando uma nova conta
        Conta novaConta = new Conta();
        novaConta.setTitular(titular);
        novaConta.setNumeroConta(numeroConta);
        novaConta.setSaldo(BigDecimal.ZERO); // Saldo inicial é 0
        novaConta.setSaldoEspecial(saldoEspecial);
        novaConta.setSituacaoConta(SituacaoConta.ATIVA); // Usando o enum SituacaoConta

        // Salvando a nova conta no banco de dados
        return contaRepository.save(novaConta);
    }
}
