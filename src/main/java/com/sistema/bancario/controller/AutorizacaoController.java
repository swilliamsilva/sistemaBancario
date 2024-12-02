package com.sistema.bancario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AutorizacaoController {

    @GetMapping("/autorizacao")
    public String administracao() {
        return "transacoes"; // Nome do arquivo XHTML sem extensão
    }
}
