package com.sistema.bancario.controller;

import com.sistema.bancario.model.Conta;
import com.sistema.bancario.repository.ContaRepository;
import com.sistema.bancario.service.ContaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/contas")
public class ContaController {

    private final ContaService contaService;
    private final ContaRepository contaRepository;

    // Injeção via construtor
    public ContaController(ContaService contaService, ContaRepository contaRepository) {
        this.contaService = contaService;
        this.contaRepository = contaRepository;
    }

    /**
     * Exibe a página de listagem de contas.
     */
    
    @GetMapping
    public String listarContas(@RequestParam(value = "titular", required = false) String titular, Model model) {
        List<Conta> contas;
        if (titular != null && !titular.isEmpty()) {
            // Busca contas pelo nome do titular
            contas = contaRepository.findByTitularContainingIgnoreCase(titular);
            model.addAttribute("mensagem", "Resultados para: " + titular);
        } else {
            // Lista todas as contas se o titular não for especificado
            contas = contaRepository.findAll();
        }
        model.addAttribute("contas", contas);
        model.addAttribute("titular", titular);
        return "listar-contas";
    }

    
    /**
     * Exibe o formulário para criação de uma nova conta.
     */
    @GetMapping("/criar")
    public String exibirFormularioCriarConta(Model model) {
        model.addAttribute("conta", new Conta());
        return "criar-conta";
    }

    /**
     * Cria uma nova conta e redireciona para a listagem de contas.
     */
    @PostMapping("/criar")
    public String criarConta(@ModelAttribute Conta conta, Model model) {
        try {
            contaRepository.save(conta);
            model.addAttribute("mensagem", "Conta criada com sucesso.");
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao criar conta: " + e.getMessage());
        }
        return "redirect:/contas";
    }

    /**
     * Exibe o formulário para realizar transferências.
     */
    @GetMapping("/transferir")
    public String exibirFormularioTransferencia(Model model) {
        model.addAttribute("idContaOrigem", null);
        model.addAttribute("idContaDestino", null);
        model.addAttribute("valor", null);
        return "transferir";
    }

    /**
     * Realiza uma transferência entre contas.
     */
    @PostMapping("/transferir")
    public String transferir(@RequestParam Long idContaOrigem,
                             @RequestParam Long idContaDestino,
                             @RequestParam Double valor,
                             Model model) {
        try {
            contaService.transferir(idContaOrigem, idContaDestino, valor);
            model.addAttribute("mensagem", "Transferência realizada com sucesso.");
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
        }
        return "transferir";
    }
}
