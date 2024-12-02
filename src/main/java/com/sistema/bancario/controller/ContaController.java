package com.sistema.bancario.controller;

import com.sistema.bancario.model.Conta;
import com.sistema.bancario.repository.ContaRepository;
import com.sun.faces.action.RequestMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaRepository contaRepository;

    @GetMapping
    public String listarContas(Model model) {
        // Verifique se a lista de contas está sendo preenchida corretamente
        List<Conta> contas = contaRepository.findAll();  // Recupera todas as contas
        model.addAttribute("contas", contas);  // Adiciona a lista ao modelo
        return "listar-contas";  // Retorna o nome da view a ser renderizada
    }
}
