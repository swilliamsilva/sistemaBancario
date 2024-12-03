package com.sistema.bancario.repository;

import com.sistema.bancario.model.Conta;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {

	Optional<Conta> findByNumeroConta(String numeroConta);
}
