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

    private static final long serialVersionUID = 1L; // Identificador único para a classe

    @Inject
    private ContaRepository contaRepository;

    private String titular;
    private BigDecimal saldo;
    private BigDecimal saldoEspecial;
    private Long idContaOrigem;
    private Long idContaDestino;
    private BigDecimal valor;

    // Método para criar conta
    public void criarConta() {
        // Validação de entrada
        if (titular == null || titular.isEmpty() || saldoEspecial == null || saldoEspecial.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Dados inválidos para criar a conta.");
        }

        // Cria nova conta
        Conta novaConta = new Conta();
        novaConta.setTitular(titular);
        novaConta.setSaldo(saldo != null ? saldo : BigDecimal.ZERO); // Saldo inicial é opcional, se não for informado será 0
        novaConta.setSaldoEspecial(saldoEspecial);
        novaConta.setSituacaoConta(SituacaoConta.ATIVA); // Usando a enum SituacaoConta

        // Salva a nova conta no repositório
        contaRepository.save(novaConta);
    }

    // Método para realizar transferência entre contas
    public void transferir() {
        // Validações de entrada
        if (idContaOrigem == null || idContaDestino == null || valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Dados inválidos para transferência.");
        }

        // Busca as contas de origem e destino
        Conta contaOrigem = contaRepository.findById(idContaOrigem)
            .orElseThrow(() -> new RuntimeException("Conta de origem não encontrada."));
        Conta contaDestino = contaRepository.findById(idContaDestino)
            .orElseThrow(() -> new RuntimeException("Conta de destino não encontrada."));

        // Verifica saldo suficiente, considerando o saldo especial
        BigDecimal saldoTotalOrigem = contaOrigem.getSaldo().add(contaOrigem.getSaldoEspecial());
        if (saldoTotalOrigem.compareTo(valor) < 0) {
            throw new RuntimeException("Saldo insuficiente na conta de origem.");
        }

        // Atualiza os saldos da conta de origem
        if (contaOrigem.getSaldo().compareTo(valor) >= 0) {
            contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));
        } else {
            BigDecimal restante = valor.subtract(contaOrigem.getSaldo());
            contaOrigem.setSaldo(BigDecimal.ZERO);
            contaOrigem.setSaldoEspecial(contaOrigem.getSaldoEspecial().subtract(restante));
        }

        // Atualiza o saldo da conta de destino
        contaDestino.setSaldo(contaDestino.getSaldo().add(valor));

        // Salva as alterações no banco de dados
        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);
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
