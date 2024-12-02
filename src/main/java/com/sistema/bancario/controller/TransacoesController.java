package com.sistema.bancario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransacoesController {

    @GetMapping("/transacoes")
    public String transacoes() {
        return "transacoes"; // Nome do arquivo XHTML sem extensão
    }
}
