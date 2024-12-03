package com.sistema.bancario.controller;

import com.sistema.bancario.model.Conta;
import com.sistema.bancario.service.ContaService;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContaController {

    @Autowired
    private ContaService contaService;

    // Mapeamento para criar a conta
    @PostMapping("/criar")
    public String criarConta(@ModelAttribute Conta conta, Model model) {
        // Criação de uma nova conta
        Conta novaConta = contaService.criarConta(conta.getTitular(), conta.getNumeroConta(), conta.getSaldoEspecial());

        // Adicionando uma mensagem de sucesso
        model.addAttribute("mensagemSucesso", "Conta criada com sucesso!");

        // Redireciona para a página de lista de contas
        return "redirect:/menucontas";
    }

    // Mapeamento para creditar valor na conta
    @PostMapping("/depositar")
    public String creditar(@ModelAttribute Long contaId, @ModelAttribute BigDecimal valor, Model model) {
        try {
            contaService.creditar(contaId, valor);
            model.addAttribute("mensagemSucesso", "Depósito realizado com sucesso!");
        } catch (Exception e) {
            model.addAttribute("mensagemErro", "Erro ao realizar depósito: " + e.getMessage());
        }
        return "redirect:/menucontas"; // Redireciona para a lista de contas após operação
    }

    // Mapeamento para debitar valor da conta
    @PostMapping("/sacar")
    public String debitar(@ModelAttribute Long contaId, @ModelAttribute BigDecimal valor, Model model) {
        try {
            contaService.debitar(contaId, valor);
            model.addAttribute("mensagemSucesso", "Saque realizado com sucesso!");
        } catch (Exception e) {
            model.addAttribute("mensagemErro", "Erro ao realizar saque: " + e.getMessage());
        }
        return "redirect:/menucontas"; // Redireciona para a lista de contas após operação
    }
}
