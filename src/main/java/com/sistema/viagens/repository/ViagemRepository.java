package com.sistema.viagens.repository;

import com.sistema.viagens.model.Viagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ViagemRepository extends JpaRepository<Viagem, Long> {
    List<Viagem> findByClienteId(Long clienteId);
    List<Viagem> findByDataPartidaBetween(LocalDateTime inicio, LocalDateTime fim);
} 