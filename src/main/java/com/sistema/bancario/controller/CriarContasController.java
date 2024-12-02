package com.sistema.bancario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CriarContasController {

    @GetMapping("/criar-contas")
    public String criarContas() {
           return "criar-contas";
    }
}
