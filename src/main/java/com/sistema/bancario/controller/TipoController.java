package com.sistema.bancario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TipoController {

    @GetMapping("/tipo")
    public String tipoextrato() {
        return "tipo"; // Nome do arquivo XHTML sem extensão
    }
}
