package com.sistema.bancario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sistema.bancario.model.Conta;
import java.util.List;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    // Definição do método personalizado
    List<Conta> findByTitularContainingIgnoreCase(String titular);
}
