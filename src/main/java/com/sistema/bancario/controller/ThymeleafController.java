package com.sistema.bancario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeleafController {

    @GetMapping("/home3") // Rota exclusiva para Thymeleaf
    public String index3() {
        return "/index3"; // Duplicar arquivo index3.html
    }

    @GetMapping("/criar-conta3") // Rota alterada
    public String criarConta() {
        return "/criar-conta3"; // Duplicar arquivo criar-conta3.html
    }

    @GetMapping("/listar-contas3") // Rota alterada
    public String listarContas3() {
        return "/listar-contas3"; // Duplicar arquivo listar-contas3.html
    }
    
    @GetMapping("/transferir3") // Rota alterada
    public String transferir() {
        return "/transferir3"; // Duplicar arquivo transferir3.html
    }
}
