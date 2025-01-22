package com.sistema.bancario.controller;

import com.sistema.bancario.model.Conta;
import com.sistema.bancario.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class ContasController {

    @Autowired
    private ContaService contaService;

    // Método para listar contas
    @GetMapping("/listar-contas")
    public String listarContas(Model model) {
        List<Conta> contas = contaService.listarTodasAsContas();
        model.addAttribute("contas", contas);
        return "listar-contas";
    }

    // Método para criar formulário de conta
    @GetMapping("/contas/criar")
    public String criarContaForm(Model model) {
        model.addAttribute("conta", new Conta());
        return "criar-contas";
    }

    // Método para salvar nova conta
    @PostMapping("/contas/criar")
    public String criarConta(Conta conta, Model model) {
        BigDecimal saldoInicial = conta.getSaldoEspecial() != null ? conta.getSaldoEspecial() : BigDecimal.ZERO;

        try {
            contaService.criarConta(conta.getTitular(), saldoInicial);
        } catch (Exception e) {
            model.addAttribute("mensagemErro", "Erro ao criar conta: " + e.getMessage());
            return "criar-contas";
        }

        return "redirect:/listar-contas";
    }
}
