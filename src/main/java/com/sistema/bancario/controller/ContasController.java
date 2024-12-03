package com.sistema.bancario.controller;

import com.sistema.bancario.model.Conta;
import com.sistema.bancario.service.ContaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ContasController {

    private static final Logger logger = LoggerFactory.getLogger(ContasController.class);

    @Autowired
    private ContaService contaService;

    // Método para listar contas
    @GetMapping("/listar-contas")
    public String listarContas(Model model) {
        logger.info("Iniciando processo de listagem de contas.");

        try {
            // Buscar todas as contas
            List<Conta> contas = contaService.listarTodasAsContas();

            // Adicionar as contas ao modelo
            model.addAttribute("contas", contas);

            logger.info("Listagem de contas concluída com sucesso. Total de contas encontradas: {}", contas.size());
        } catch (Exception e) {
            logger.error("Erro ao listar contas: {}", e.getMessage(), e);
            model.addAttribute("mensagemErro", "Erro ao listar contas: " + e.getMessage());
        }

        return "listar-contas"; // Retorna a página listar-contas.xhtml
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
        logger.info("Criando nova conta para o titular: {}", conta.getTitular());

        try {
            // Verificar se o titular e os dados da conta são válidos
            if (conta.getTitular() == null || conta.getTitular().isEmpty()) {
                model.addAttribute("mensagemErro", "O titular não pode ser vazio.");
                return "criar-contas";
            }
            // Chamando o método criarConta em ContaService
            contaService.criarConta(conta.getTitular(), conta.getNumeroConta(), conta.getSaldoEspecial());
            logger.info("Conta criada com sucesso para o titular: {}", conta.getTitular());
        } catch (Exception e) {
            logger.error("Erro ao criar conta: {}", e.getMessage(), e);
            model.addAttribute("mensagemErro", "Erro ao criar conta: " + e.getMessage());
            return "criar-contas"; // Retorna ao formulário com a mensagem de erro
        }

        return "redirect:/listar-contas"; // Redireciona para a página de listagem após a criação
    }
}
