package com.sistema.bancario.repository;

import com.sistema.bancario.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> findByContaIdAndDataHoraTransacaoBetween(Long contaId, LocalDateTime inicio, LocalDateTime fim);

    List<Transacao> findByTipoTransacao(String tipoTransacao);

    // Outros métodos de consulta
}
