package com.sistema.bancario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sistema.bancario.model.Transacao;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ExtratoTipoController {

    @GetMapping("/extrato/tipo")
    public String consultarExtratoPorTipo(
            @RequestParam(value = "contaId", required = true) Long contaId,
            @RequestParam(value = "tipoTransacao", required = true) String tipoTransacao,
            Model model) {

        try {
            List<Transacao> extrato = buscarTransacoesPorTipo(contaId, tipoTransacao);

            if (extrato.isEmpty()) {
                model.addAttribute("mensagemErro", "Nenhuma transação encontrada para o tipo selecionado.");
            } else {
                model.addAttribute("extrato", extrato);
            }
        } catch (Exception e) {
            model.addAttribute("mensagemErro", "Erro ao buscar extrato: " + e.getMessage());
        }

        return "tipo"; // Nome do arquivo na pasta `templates`
    }

    private List<Transacao> buscarTransacoesPorTipo(Long contaId, String tipoTransacao) {
        List<Transacao> transacoes = new ArrayList<>();
    //    if ("DEPOSITO".equalsIgnoreCase(tipoTransacao)) {
    //        transacoes.add(new Transacao("2024-11-01", "DEPÓSITO", 1000.00, 2000.00));
    //    } else if ("SAQUE".equalsIgnoreCase(tipoTransacao)) {
    //        transacoes.add(new Transacao("2024-11-02", "SAQUE", -200.00, 1800.00));
    //    } else if ("TRANSFERENCIA".equalsIgnoreCase(tipoTransacao)) {
    //        transacoes.add(new Transacao("2024-11-03", "TRANSFERÊNCIA", -500.00, 1300.00));
     //   }
        return transacoes;
    }
}
