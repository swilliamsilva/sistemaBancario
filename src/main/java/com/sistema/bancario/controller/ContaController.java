package com.sistema.bancario.controller;

import com.sistema.bancario.model.Conta;
import com.sistema.bancario.repository.ContaRepository;
import com.sistema.bancario.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @Autowired
    private ContaRepository contaRepository;

    // Exibir página de listagem de contas
    @GetMapping
    public String listarContas(Model model) {
        List<Conta> contas = contaRepository.findAll();
        model.addAttribute("contas", contas);
        return "listar-contas"; // Nome da página Thymeleaf
    }

    // Exibir página de criação de conta
    @GetMapping("/criar")
    public String exibirFormularioCriarConta(Model model) {
        model.addAttribute("conta", new Conta());
        return "criar-conta";
    }

    // Criar conta
    @PostMapping("/criar")
    public String criarConta(@ModelAttribute Conta conta) {
        contaRepository.save(conta);
        return "redirect:/contas"; // Redirecionar para a lista de contas
    }

    // Exibir página de transferência
    @GetMapping("/transferir")
    public String exibirFormularioTransferencia(Model model) {
        model.addAttribute("idContaOrigem", null);
        model.addAttribute("idContaDestino", null);
        model.addAttribute("valor", null);
        return "transferir";
    }

    // Realizar transferência
    @PostMapping("/transferir")
    public String transferir(@RequestParam Long idContaOrigem, @RequestParam Long idContaDestino, @RequestParam Double valor, Model model) {
        try {
            contaService.transferir(idContaOrigem, idContaDestino, valor);
            model.addAttribute("mensagem", "Transferência realizada com sucesso.");
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
        }
        return "transferir";
    }
}
