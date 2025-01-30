package com.sistema.bancario.repository;

import com.sistema.bancario.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    // Buscar transações por tipo
    List<Transacao> findByContaIdAndTipoTransacao(Long contaId, String tipoTransacao);

    @Query("SELECT t FROM Transacao t WHERE t.conta.numeroConta = :numeroConta " +
           "AND t.dataHoraTransacao BETWEEN :inicio AND :fim " +
           "ORDER BY t.dataHoraTransacao DESC")
    List<Transacao> findByContaNumeroContaAndDataHoraTransacaoBetween(
        @Param("numeroConta") String numeroConta,
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );

    // Outros métodos de consulta
}
