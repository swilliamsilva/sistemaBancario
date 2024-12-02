package com.sistema.bancario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ExtratoTipoController {

    @GetMapping("/extrato/tipo")
    public String consultarExtratoPorTipo(
            @RequestParam("contaId") Long contaId,
            @RequestParam("tipoTransacao") String tipoTransacao,
            Model model) {

        try {
            // Substituir a lógica abaixo por chamada ao serviço para buscar transações
            List<Transacao> extrato = buscarTransacoesPorTipo(contaId, tipoTransacao);

            if (extrato.isEmpty()) {
                model.addAttribute("extrato", null);
            } else {
                model.addAttribute("extrato", extrato);
            }
        } catch (Exception e) {
            model.addAttribute("mensagemErro", "Erro ao buscar extrato: " + e.getMessage());
        }

        return "transacoes"; // Nome da página do Thymeleaf
    }

    private List<Transacao> buscarTransacoesPorTipo(Long contaId, String tipoTransacao) {
        // Exemplo de retorno simulado (substituir pela lógica real do serviço/repositório)
        List<Transacao> transacoes = new ArrayList<>();
        if ("DEPOSITO".equals(tipoTransacao)) {
            transacoes.add(new Transacao("2024-11-01", "DEPÓSITO", 1000.00, 2000.00));
        } else if ("SAQUE".equals(tipoTransacao)) {
            transacoes.add(new Transacao("2024-11-02", "SAQUE", -200.00, 1800.00));
        } else if ("TRANSFERENCIA".equals(tipoTransacao)) {
            transacoes.add(new Transacao("2024-11-03", "TRANSFERÊNCIA", -500.00, 1300.00));
        }
        return transacoes;
    }
}

class Transacao {
    private String dataHoraTransacao;
    private String tipoTransacao;
    private Double valor;
    private Double saldoRestante;

    public Transacao(String dataHoraTransacao, String tipoTransacao, Double valor, Double saldoRestante) {
        this.dataHoraTransacao = dataHoraTransacao;
        this.tipoTransacao = tipoTransacao;
        this.valor = valor;
        this.saldoRestante = saldoRestante;
    }

    // Getters e Setters
    public String getDataHoraTransacao() {
        return dataHoraTransacao;
    }

    public void setDataHoraTransacao(String dataHoraTransacao) {
        this.dataHoraTransacao = dataHoraTransacao;
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getSaldoRestante() {
        return saldoRestante;
    }

    public void setSaldoRestante(Double saldoRestante) {
        this.saldoRestante = saldoRestante;
    }
}
