package com.sistema.bancario.controller;

import com.sistema.bancario.model.Transacao;
import com.sistema.bancario.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ExtratoController {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @GetMapping("/extrato")
    public String consultarExtrato(
            @RequestParam("contaId") Long contaId,
            @RequestParam("dataInicio") String dataInicio,
            @RequestParam("dataFim") String dataFim,
            Model model) {

        try {
            // Converte as datas recebidas para LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime inicio = LocalDateTime.parse(dataInicio + " 00:00:00", formatter);
            LocalDateTime fim = LocalDateTime.parse(dataFim + " 23:59:59", formatter);

            // Busca transações no repositório
            List<Transacao> extrato = transacaoRepository.findByContaIdAndDataHoraBetween(contaId, inicio, fim);

            model.addAttribute("extrato", extrato);

        } catch (Exception e) {
            model.addAttribute("mensagemErro", "Erro ao buscar extrato: " + e.getMessage());
        }

        return "transacoes"; // Nome da página do Thymeleaf
    }
}
