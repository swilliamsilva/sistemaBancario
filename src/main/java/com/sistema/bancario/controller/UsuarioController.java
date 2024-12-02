package com.sistema.bancario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {

    @GetMapping("/usuarios")
    public String administracao() {
        return "usuarios"; // Nome do arquivo XHTML sem extensão
    }
}