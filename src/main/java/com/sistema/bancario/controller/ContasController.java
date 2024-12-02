package com.sistema.bancario.controller;

import com.sistema.bancario.model.Conta;
import com.sistema.bancario.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/contas")
public class ContasController {

    @Autowired
    private ContaService contaService;

    @GetMapping("/criar")
    public String criarContaForm(Model model) {
        model.addAttribute("conta", new Conta());
        return "criar-contas";  // Garante que o Thymeleaf vai procurar o arquivo criar-contas.xhtml
    }

    @PostMapping("/criar")
    public String criarConta(@ModelAttribute Conta conta) {
        contaService.criarConta(conta.getTitular(), conta.getNumeroConta(), conta.getSaldoEspecial());
        return "redirect:/menucontas";  // Redireciona para a lista de contas
    }
}
