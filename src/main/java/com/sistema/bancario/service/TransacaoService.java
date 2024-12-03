package com.sistema.bancario.service;

import com.sistema.bancario.model.Transacao;
import com.sistema.bancario.repository.TransacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransacaoService {

    private static final Logger logger = LoggerFactory.getLogger(TransacaoService.class);

    @Autowired
    private TransacaoRepository transacaoRepository;

    public List<Transacao> buscarTransacoesPorPeriodo(Long contaId, LocalDateTime inicio, LocalDateTime fim) {
        logger.debug("Buscando transações no repositório. ContaId: {}, Inicio: {}, Fim: {}", contaId, inicio, fim);
        return transacaoRepository.findByContaIdAndDataHoraTransacaoBetween(contaId, inicio, fim);
    }

    public BigDecimal calcularTotalTransacoes(List<Transacao> transacoes) {
        logger.debug("Calculando o total de {} transações.", transacoes.size());
        return transacoes.stream()
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
