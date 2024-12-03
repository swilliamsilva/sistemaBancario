package com.sistema.bancario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContaController {

    @GetMapping("/detalhesConta")
    public String detalhesConta(@RequestParam(value = "contaId", required = false) Long contaId, Model model) {
        if (contaId == null) {
            // Caso o parâmetro 'contaId' não seja fornecido
            model.addAttribute("mensagemErro", "O parâmetro 'contaId' é obrigatório para acessar esta página.");
            return "erro-pagina"; // Redireciona para uma página de erro personalizada
        }

        // Adicionar lógica de busca de detalhes da conta
        // Exemplo: Conta conta = contaService.buscarContaPorId(contaId);
        model.addAttribute("contaId", contaId); // Adiciona os dados ao modelo
        return "detalhesConta"; // Nome da página Thymeleaf para exibir os detalhes
    }
}
