package com.sistema.bancario.service;

import com.sistema.bancario.model.Conta;
import com.sistema.bancario.model.SituacaoConta;
import com.sistema.bancario.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    // Método para buscar uma conta pelo ID
    public Conta buscarContaPorId(Long contaId) {
        return contaRepository.findById(contaId).orElse(null);
    }

    // Método para criar uma nova conta
    public Conta criarConta(String nomeCompleto, BigDecimal saldoInicial) {
        if (saldoInicial == null) {
            saldoInicial = BigDecimal.ZERO;
        }

        Conta novaConta = new Conta();
        novaConta.setTitular(nomeCompleto);  // Nome completo do titular
        novaConta.setSaldo(saldoInicial);
        novaConta.setSaldoEspecial(BigDecimal.ZERO);  // Saldo especial padrão
        novaConta.setSituacaoConta(SituacaoConta.ATIVA);  // Conta ativa por padrão

        return contaRepository.save(novaConta);
    }

    // Método para realizar transferência entre contas
    public void transferir(Long idContaOrigem, Long idContaDestino, BigDecimal valor) {
        Conta contaOrigem = buscarContaPorId(idContaOrigem);
        Conta contaDestino = buscarContaPorId(idContaDestino);

        if (contaOrigem == null || contaDestino == null) {
            throw new IllegalArgumentException("Conta(s) não encontrada(s).");
        }

        // Verificação do saldo total
        BigDecimal saldoTotalOrigem = contaOrigem.getSaldo().add(contaOrigem.getSaldoEspecial());
        if (saldoTotalOrigem.compareTo(valor) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente na conta de origem.");
        }

        // Atualização dos saldos
        if (contaOrigem.getSaldo().compareTo(valor) >= 0) {
            contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));
        } else {
            BigDecimal restante = valor.subtract(contaOrigem.getSaldo());
            contaOrigem.setSaldo(BigDecimal.ZERO);
            contaOrigem.setSaldoEspecial(contaOrigem.getSaldoEspecial().subtract(restante));
        }

        contaDestino.setSaldo(contaDestino.getSaldo().add(valor));

        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);
    }

    // Método para listar todas as contas
    public List<Conta> listarTodasAsContas() {
        return contaRepository.findAll();
    }

    // Método para creditar valor em uma conta
    public void creditar(Long contaId, BigDecimal valor) {
        Conta conta = buscarContaPorId(contaId);
        if (conta == null) {
            throw new IllegalArgumentException("Conta não encontrada.");
        }

        // Credita o valor no saldo
        conta.setSaldo(conta.getSaldo().add(valor));
        contaRepository.save(conta);
    }

    // Método para debitar valor de uma conta
    public void debitar(Long contaId, BigDecimal valor) {
        Conta conta = buscarContaPorId(contaId);
        if (conta == null) {
            throw new IllegalArgumentException("Conta não encontrada.");
        }

        BigDecimal saldoTotal = conta.getSaldo().add(conta.getSaldoEspecial());

        if (saldoTotal.compareTo(valor) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }

        // Debita o valor da conta
        if (conta.getSaldo().compareTo(valor) >= 0) {
            conta.setSaldo(conta.getSaldo().subtract(valor));
        } else {
            BigDecimal restante = valor.subtract(conta.getSaldo());
            conta.setSaldo(BigDecimal.ZERO);
            conta.setSaldoEspecial(conta.getSaldoEspecial().subtract(restante));
        }

        contaRepository.save(conta);
    }
}
