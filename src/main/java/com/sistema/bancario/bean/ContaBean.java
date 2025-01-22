package com.sistema.bancario.bean;

import com.sistema.bancario.model.Conta;
import com.sistema.bancario.model.SituacaoConta;
import com.sistema.bancario.repository.ContaRepository;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@ManagedBean
@ViewScoped
public class ContaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ContaRepository contaRepository;

    private String titular;  // Nome completo do titular
    private BigDecimal saldo;
    private BigDecimal saldoEspecial;
    private Long idContaOrigem;
    private Long idContaDestino;
    private BigDecimal valor;

    // Método para criar conta
    public void criarConta() {
        if (titular == null || titular.isEmpty() || saldoEspecial == null || saldoEspecial.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Dados inválidos para criar a conta.");
        }

        Conta novaConta = new Conta();
        novaConta.setTitular(titular);
        novaConta.setSaldo(saldo != null ? saldo : BigDecimal.ZERO);
        novaConta.setSaldoEspecial(saldoEspecial);
        novaConta.setSituacaoConta(SituacaoConta.ATIVA); 

        contaRepository.save(novaConta);
    }

    // Método para listar todas as contas
    public List<Conta> getContas() {
        return contaRepository.findAll();
    }

    // Getters e Setters
    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
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

    public Long getIdContaOrigem() {
        return idContaOrigem;
    }

    public void setIdContaOrigem(Long idContaOrigem) {
        this.idContaOrigem = idContaOrigem;
    }

    public Long getIdContaDestino() {
        return idContaDestino;
    }

    public void setIdContaDestino(Long idContaDestino) {
        this.idContaDestino = idContaDestino;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
