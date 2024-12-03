package com.sistema.bancario.service;

import com.sistema.bancario.model.Transacao;
import com.sistema.bancario.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    // Método para buscar transações por conta e período
    public List<Transacao> buscarTransacoesPorPeriodo(Long contaId, LocalDateTime inicio, LocalDateTime fim) {
        return transacaoRepository.findByContaIdAndDataHoraTransacaoBetween(contaId, inicio, fim);
    }

    // Método para calcular o total das transações
    public BigDecimal calcularTotalTransacoes(List<Transacao> transacoes) {
        return transacoes.stream()
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
