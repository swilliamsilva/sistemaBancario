package com.sistema.bancario.service;

import com.sistema.bancario.model.Conta;
import com.sistema.bancario.model.SituacaoConta;
import com.sistema.bancario.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

    public Conta buscarPorNumero(String numeroConta) {
        return contaRepository.findByNumeroConta(numeroConta)
            .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
    }

    @Transactional
    public boolean realizarSaque(String numeroConta, BigDecimal valor) {
        return contaRepository.findByNumeroConta(numeroConta)
            .map(conta -> {
                if (conta.getSaldo().compareTo(valor) >= 0) {
                    conta.setSaldo(conta.getSaldo().subtract(valor));
                    contaRepository.save(conta);
                    return true;
                }
                return false;
            })
            .orElse(false);
    }

    @Transactional
    public boolean realizarDeposito(String numeroConta, BigDecimal valor) {
        return contaRepository.findByNumeroConta(numeroConta)
            .map(conta -> {
                conta.setSaldo(conta.getSaldo().add(valor));
                contaRepository.save(conta);
                return true;
            })
            .orElse(false);
    }

    @Transactional
    public boolean realizarTransferencia(String contaOrigem, String contaDestino, BigDecimal valor) {
        Optional<Conta> contaOrigemOpt = contaRepository.findByNumeroConta(contaOrigem);
        Optional<Conta> contaDestinoOpt = contaRepository.findByNumeroConta(contaDestino);

        if (contaOrigemOpt.isPresent() && contaDestinoOpt.isPresent()) {
            Conta origem = contaOrigemOpt.get();
            Conta destino = contaDestinoOpt.get();

            if (origem.getSaldo().compareTo(valor) >= 0) {
                origem.setSaldo(origem.getSaldo().subtract(valor));
                destino.setSaldo(destino.getSaldo().add(valor));
                
                contaRepository.save(origem);
                contaRepository.save(destino);
                return true;
            }
        }
        return false;
    }
}
