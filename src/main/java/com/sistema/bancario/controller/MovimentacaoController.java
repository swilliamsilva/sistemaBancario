package com.sistema.bancario.controller;

import com.sistema.bancario.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class MovimentacaoController {

    @Autowired
    private ContaService contaService;

    @PostMapping("/movimentacao")
    public String movimentar(
            @RequestParam("contaId") Long contaId,
            @RequestParam("tipoTransacao") String tipoTransacao,
            @RequestParam(value = "valor", required = false) Double valor,
            Model model) {

        try {
            switch (tipoTransacao) {
                case "DEPOSITO":
                    contaService.creditar(contaId, BigDecimal.valueOf(valor));
                    model.addAttribute("mensagemSucesso", "Depósito realizado com sucesso!");
                    break;
                case "SAQUE":
                    contaService.debitar(contaId, BigDecimal.valueOf(valor));
                    model.addAttribute("mensagemSucesso", "Saque realizado com sucesso!");
                    break;
                case "CONSULTAR_SALDO":
                    Conta conta = contaService.buscarContaPorId(contaId);
                    model.addAttribute("mensagemSucesso", "Saldo disponível: R$ " + conta.getSaldo());
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de transação inválido.");
            }
        } catch (Exception e) {
            model.addAttribute("mensagemErro", "Erro ao processar a transação: " + e.getMessage());
        }

        return "transacoes";
    }
}
