package com.sistema.bancario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/app") // Página inicial da aplicação
    public String appIndex() {
        return "appIndex"; // Nome do arquivo da página
    }

    @GetMapping("/criar-conta") // Página de criar conta
    public String criarConta() {
        return "criar-conta"; // Nome do arquivo sem a extensão
    }

    // REMOVER ESTA LINHA:
    // @GetMapping("/listar-contas") // Página de listar contas
    // public String listarContas() {
    //     return "listar-contas"; // Nome do arquivo sem a extensão
    // }

    @GetMapping("/transferir") // Página de transferências
    public String transferir() {
        return "transferir"; // Nome do arquivo sem a extensão
    }
}
