package com.sistema.bancario.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Usuario {

    @Id
    private Long id;

    // Getters e Setters
    public Long getId() {
        return id;
    }
}
