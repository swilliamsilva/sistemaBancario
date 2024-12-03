package com.sistema.bancario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MovimentacaoDeContaController {

    @GetMapping("/movimentacaoDeConta")
    public String movimentacaoDeConta() {
        return "movimentacaoDeConta"; // Nome do arquivo XHTML sem extensão
    }
}