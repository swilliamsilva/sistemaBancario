package com.sistema.bancario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sistema.bancario.model.Conta;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    // Busca contas pelo nome do titular, ignorando maiúsculas e minúsculas
    List<Conta> findByTitularContainingIgnoreCase(String titular);

    // Busca todas as contas, ordenadas por titular
    List<Conta> findAllByOrderByTitularAsc();  // Método para ordenar as contas por titular (nome)

    // Busca uma conta pelo número da conta
    Optional<Conta> findByNumeroConta(String numeroConta);
}
