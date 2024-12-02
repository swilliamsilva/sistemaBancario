package com.sistema.bancario.repository;

import com.sistema.bancario.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByContaIdAndDataHoraBetween(Long contaId, LocalDateTime inicio, LocalDateTime fim);
}
