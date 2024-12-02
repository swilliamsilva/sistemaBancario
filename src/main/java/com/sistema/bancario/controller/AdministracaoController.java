package com.sistema.bancario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdministracaoController {

    @GetMapping("/administracao")
    public String administracao() {
        return "administracao"; // Nome do arquivo XHTML sem extensão
    }
}
