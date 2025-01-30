package com.sistema.bancario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administracao")
public class AdministracaoController {

    @GetMapping
    public String administracao() {
        return "administracao"; // Nome do arquivo XHTML sem extensão
    }
}
