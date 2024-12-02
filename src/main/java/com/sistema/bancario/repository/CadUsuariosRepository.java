package com.sistema.bancario.repository;

import com.sistema.bancario.model.CadUsuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CadUsuariosRepository extends JpaRepository<CadUsuarios, Long> {
}
