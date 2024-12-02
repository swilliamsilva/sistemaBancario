package com.sistema.bancario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuContasController {

    @GetMapping("/menucontas")
    public String administracao() {
        return "menucontas"; // Nome do arquivo XHTML sem extensão
    }
}
