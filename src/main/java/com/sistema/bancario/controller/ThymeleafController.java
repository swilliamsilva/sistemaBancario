package com.sistema.bancario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeleafController {

    @GetMapping("/")
    public String index() {
        return "index"; // Nome do arquivo da página inicial sem a extensão
    }

    @GetMapping("/criar-conta")
    public String criarConta() {
        return "criar-conta"; // Nome do arquivo sem a extensão
    }

    @GetMapping("/listar-contas")
    public String listarContas() {
        return "listar-contas"; // Nome do arquivo sem a extensão
    }

    @GetMapping("/transferir")
    public String transferir() {
        return "transferir"; // Nome do arquivo sem a extensão
    }
}
