package com.sistema.bancario.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Conta conta; // Conta associada à transação

    @Column(nullable = false)
    private String tipo; // Tipo da transação: "Crédito" ou "Débito"

    @Column(nullable = false)
    private BigDecimal valor; // Valor da transação

    @Column(nullable = false)
    private BigDecimal valorUsadoEspecial; // Valor usado do saldo especial

    @Column(nullable = false)
    private LocalDateTime dataHora; // Data e hora da transação

    // Construtores
    public Transacao() {}

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValorUsadoEspecial() {
        return valorUsadoEspecial;
    }

    public void setValorUsadoEspecial(BigDecimal valorUsadoEspecial) {
        this.valorUsadoEspecial = valorUsadoEspecial;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
