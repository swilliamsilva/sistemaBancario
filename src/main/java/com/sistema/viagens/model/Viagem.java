package com.sistema.viagens.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Viagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    private String destino;
    private LocalDateTime dataPartida;
    private LocalDateTime dataRetorno;
    private BigDecimal valor;
    private String status;
    
    // Getters e Setters
} 