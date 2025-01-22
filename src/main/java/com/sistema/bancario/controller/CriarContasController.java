package com.sistema.bancario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CriarContasController {

    // REMOVER ESTE MÉTODO para evitar redundância
    // @GetMapping("/criar-contas")
    // public String criarContas() {
    //     return "criar-contas";
    // }

    @GetMapping("/administracao")
    public String mostrarAdministracao() {
        return "administracao";
    }
}
