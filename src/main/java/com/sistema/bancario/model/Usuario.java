package com.sistema.bancario.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true, length = 8)
    private String registro;

    private String usuario;

    private String senha;

    private String role; // ADMIN ou TITULAR

    // Getters, Setters, Construtores
}
