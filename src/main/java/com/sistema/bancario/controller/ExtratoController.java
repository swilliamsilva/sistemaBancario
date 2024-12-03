package com.sistema.bancario.controller;

import com.sistema.bancario.model.Transacao;
import com.sistema.bancario.service.TransacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Controller
public class ExtratoController {

    private static final Logger logger = LoggerFactory.getLogger(ExtratoController.class);

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping("/extrato")
    public String exibirExtrato(
            @RequestParam(value = "contaId", required = true) Long contaId,
            @RequestParam(value = "inicio", required = false) LocalDateTime inicio,
            @RequestParam(value = "fim", required = false) LocalDateTime fim,
            Model model) {

        logger.info("Iniciando consulta de extrato. ContaId: {}", contaId);

        try {
            // Definir datas padrão, se necessário
            if (inicio == null) {
                inicio = LocalDateTime.now().minusMonths(1);
                logger.debug("Data inicial não fornecida. Usando valor padrão: {}", inicio);
            }
            if (fim == null) {
                fim = LocalDateTime.now();
                logger.debug("Data final não fornecida. Usando valor padrão: {}", fim);
            }

            logger.debug("Buscando transações para a conta {} entre {} e {}", contaId, inicio, fim);

            // Buscar transações e calcular total
            List<Transacao> transacoes = transacaoService.buscarTransacoesPorPeriodo(contaId, inicio, fim);
            BigDecimal total = transacaoService.calcularTotalTransacoes(transacoes);

            model.addAttribute("transacoes", transacoes);
            model.addAttribute("total", total);

            logger.info("Consulta de extrato concluída com sucesso. Total de transações: {}", transacoes.size());
        } catch (Exception e) {
            logger.error("Erro ao gerar extrato para a conta {}: {}", contaId, e.getMessage(), e);
            model.addAttribute("mensagemErro", "Erro ao gerar extrato: " + e.getMessage());
        }

        return "extrato";
    }
}
