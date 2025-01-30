package com.sistema.bancario.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titular; // Nome completo do titular

    @Column(nullable = false, unique = true)
    private String numeroConta;

    @Column(nullable = false)
    private BigDecimal saldo;

    @Column(nullable = false)
    private BigDecimal saldoEspecial;

    @Column(nullable = false)
    private BigDecimal limiteEspecial;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoConta situacaoConta = SituacaoConta.ATIVA; // Valor default da conta é "ATIVA"

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataHoraAbertura;

    public Conta() {
        // Construtor padrão necessário para JPA
        this.saldo = BigDecimal.ZERO;
        this.saldoEspecial = BigDecimal.ZERO;
        this.limiteEspecial = BigDecimal.ZERO;
        this.situacaoConta = SituacaoConta.ATIVA;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getSaldoEspecial() {
        return saldoEspecial;
    }

    public void setSaldoEspecial(BigDecimal saldoEspecial) {
        this.saldoEspecial = saldoEspecial;
    }

    public BigDecimal getLimiteEspecial() {
        return limiteEspecial;
    }

    public void setLimiteEspecial(BigDecimal limiteEspecial) {
        this.limiteEspecial = limiteEspecial;
    }

    public SituacaoConta getSituacaoConta() {
        return situacaoConta;
    }

    public void setSituacaoConta(SituacaoConta situacaoConta) {
        this.situacaoConta = situacaoConta;
    }

    public LocalDateTime getDataHoraAbertura() {
        return dataHoraAbertura;
    }

    public void setDataHoraAbertura(LocalDateTime dataHoraAbertura) {
        this.dataHoraAbertura = dataHoraAbertura;
    }
}
