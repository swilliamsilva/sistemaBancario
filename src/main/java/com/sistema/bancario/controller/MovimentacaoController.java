package com.sistema.bancario.controller;

import com.sistema.bancario.model.Conta;
import com.sistema.bancario.service.ContaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class MovimentacaoController {

    private static final Logger logger = LoggerFactory.getLogger(MovimentacaoController.class);

    @Autowired
    private ContaService contaService;

    @PostMapping("/movimentacao")
    public String movimentar(
            @RequestParam("contaId") Long contaId,
            @RequestParam("tipoTransacao") String tipoTransacao,
            @RequestParam(value = "valor", required = false) Double valor,
            Model model) {

        // Verificar se os parâmetros obrigatórios estão presentes
        if (contaId == null || tipoTransacao == null) {
            model.addAttribute("mensagemErro", "Dados obrigatórios não fornecidos.");
            logger.error("Erro: Dados obrigatórios não fornecidos.");
            return "movimentacaoDeConta";
        }

        try {
            switch (tipoTransacao.toUpperCase()) {
                case "DEPOSITO":
                    if (valor == null || valor <= 0) {
                        throw new IllegalArgumentException("Valor para depósito inválido.");
                    }
                    contaService.creditar(contaId, BigDecimal.valueOf(valor));
                    model.addAttribute("mensagemSucesso", "Depósito realizado com sucesso!");
                    break;

                case "SAQUE":
                    if (valor == null || valor <= 0) {
                        throw new IllegalArgumentException("Valor para saque inválido.");
                    }
                    contaService.debitar(contaId, BigDecimal.valueOf(valor));
                    model.addAttribute("mensagemSucesso", "Saque realizado com sucesso!");
                    break;

                case "CONSULTAR_SALDO":
                    Conta conta = contaService.buscarContaPorId(contaId);
                    if (conta == null) {
                        throw new IllegalArgumentException("Conta não encontrada.");
                    }
                    model.addAttribute("mensagemSucesso", "Saldo disponível: R$ " + conta.getSaldo());
                    break;

                default:
                    throw new IllegalArgumentException("Tipo de transação inválido.");
            }
        } catch (IllegalArgumentException e) {
            model.addAttribute("mensagemErro", "Erro ao processar a transação: " + e.getMessage());
            logger.error("Erro ao processar a transação: {}", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("mensagemErro", "Erro inesperado: " + e.getMessage());
            logger.error("Erro inesperado: {}", e.getMessage(), e);
        }

        return "movimentacaoDeConta";
    }
}
