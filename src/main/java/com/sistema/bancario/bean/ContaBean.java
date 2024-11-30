package com.sistema.bancario.bean;

import com.sistema.bancario.model.Conta;
import com.sistema.bancario.repository.ContaRepository;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@ViewScoped
public class ContaBean implements Serializable {

    private static final long serialVersionUID = 1L; // Identificador único para a classe

    @Inject
    private ContaRepository contaRepository;

    private String titular;
    private Double saldo;
    private Long idContaOrigem;
    private Long idContaDestino;
    private Double valor;

    public void criarConta() {
        Conta novaConta = new Conta(titular, saldo);
        contaRepository.save(novaConta);
    }

    public void transferir() {
        Conta contaOrigem = contaRepository.findById(idContaOrigem)
            .orElseThrow(() -> new RuntimeException("Conta de origem não encontrada."));
        Conta contaDestino = contaRepository.findById(idContaDestino)
            .orElseThrow(() -> new RuntimeException("Conta de destino não encontrada."));
        if (contaOrigem.getSaldo() < valor) {
            throw new RuntimeException("Saldo insuficiente na conta de origem.");
        }
        contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
        contaDestino.setSaldo(contaDestino.getSaldo() + valor);
        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);
    }

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

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
