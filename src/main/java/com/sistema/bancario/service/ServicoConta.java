package com.sistema.bancario.service;

import com.sistema.bancario.model.Conta;
import com.sistema.bancario.model.SituacaoConta;
import com.sistema.bancario.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ServicoConta {
    
    @Autowired
    private ContaRepository contaRepository;

    @Transactional
    public boolean realizarSaque(String numeroConta, BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do saque deve ser maior que zero");
        }

        Optional<Conta> contaOptional = contaRepository.findByNumeroConta(numeroConta);
        return contaOptional.map(conta -> {
            if (conta.getSituacaoConta() != SituacaoConta.ATIVA) {
                return false;
            }
            if (conta.getSaldo().compareTo(valor) >= 0) {
                conta.setSaldo(conta.getSaldo().subtract(valor));
                contaRepository.save(conta);
                return true;
            }
            return false;
        }).orElse(false);
    }

    @Transactional
    public boolean realizarDeposito(String numeroConta, BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do depósito deve ser maior que zero");
        }

        return contaRepository.findByNumeroConta(numeroConta)
            .map(conta -> {
                if (conta.getSituacaoConta() != SituacaoConta.ATIVA) {
                    return false;
                }
                conta.setSaldo(conta.getSaldo().add(valor));
                contaRepository.save(conta);
                return true;
            })
            .orElse(false);
    }

    @Transactional
    public boolean realizarTransferencia(String contaOrigem, String contaDestino, BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor da transferência deve ser maior que zero");
        }

        Optional<Conta> contaOrigemOpt = contaRepository.findByNumeroConta(contaOrigem);
        Optional<Conta> contaDestinoOpt = contaRepository.findByNumeroConta(contaDestino);

        if (contaOrigemOpt.isPresent() && contaDestinoOpt.isPresent()) {
            Conta origem = contaOrigemOpt.get();
            Conta destino = contaDestinoOpt.get();

            if (origem.getSituacaoConta() != SituacaoConta.ATIVA || 
                destino.getSituacaoConta() != SituacaoConta.ATIVA) {
                return false;
            }

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

    @Transactional
    public boolean utilizarChequeEspecial(String numeroConta, BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }

        return contaRepository.findByNumeroConta(numeroConta)
            .map(conta -> {
                if (conta.getSituacaoConta() != SituacaoConta.ATIVA) {
                    return false;
                }

                BigDecimal saldoTotal = conta.getSaldo().add(conta.getLimiteEspecial());
                if (saldoTotal.compareTo(valor) >= 0) {
                    conta.setSaldo(conta.getSaldo().subtract(valor));
                    if (conta.getSaldo().compareTo(BigDecimal.ZERO) < 0) {
                        conta.setSaldoEspecial(conta.getSaldo().abs());
                        conta.setSaldo(BigDecimal.ZERO);
                    }
                    contaRepository.save(conta);
                    return true;
                }
                return false;
            })
            .orElse(false);
    }
} 