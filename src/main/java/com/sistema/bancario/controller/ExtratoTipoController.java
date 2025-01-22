package com.sistema.bancario.controller;

import com.sistema.bancario.model.Transacao;
import com.sistema.bancario.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ExtratoTipoController {

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping("/extrato/tipo")
    public String consultarExtratoPorTipo(
            @RequestParam(value = "contaId") Long contaId,
            @RequestParam(value = "tipoTransacao") String tipoTransacao,
            Model model) {

        List<Transacao> transacoes = transacaoService.buscarTransacoesPorTipo(contaId, tipoTransacao);
        model.addAttribute("transacoes", transacoes);
        model.addAttribute("tipoTransacao", tipoTransacao);

        return "tipo"; // Nome do arquivo na pasta `templates`
    }
}
