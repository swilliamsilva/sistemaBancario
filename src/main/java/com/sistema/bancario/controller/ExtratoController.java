package com.sistema.bancario.controller;

import com.sistema.bancario.model.Transacao;
import com.sistema.bancario.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ExtratoController {

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping("/extrato")
    public String gerarExtrato(
            @RequestParam("contaId") Long contaId,
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim,
            Model model) {

        List<Transacao> transacoes = transacaoService.buscarTransacoesPorPeriodo(contaId, inicio, fim);
        BigDecimal total = transacaoService.calcularTotalTransacoes(transacoes);

        model.addAttribute("transacoes", transacoes);
        model.addAttribute("total", total);

        return "extrato"; // Nome do arquivo HTML para exibir o extrato
    }
}
