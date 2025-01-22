package com.sistema.bancario.repository;

import com.sistema.bancario.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    // Buscar transações por conta e dentro de um intervalo de datas
    List<Transacao> findByContaIdAndDataHoraTransacaoBetween(Long contaId, LocalDateTime inicio, LocalDateTime fim);

    // Buscar transações por tipo
    List<Transacao> findByContaIdAndTipoTransacao(Long contaId, String tipoTransacao);

    // Outros métodos de consulta
}
