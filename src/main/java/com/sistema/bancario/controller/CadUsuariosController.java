package com.sistema.bancario.controller;

import com.sistema.bancario.model.CadUsuarios;
import com.sistema.bancario.service.CadUsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cadusuarios")
public class CadUsuariosController {

    @Autowired
    private CadUsuariosService cadUsuariosService;

    @GetMapping
    public String listarCadUsuarios(Model model) {
        model.addAttribute("cadusuarios", cadUsuariosService.buscarTodos());
        return "cadusuarios";
    }

    @GetMapping("/criar")
    public String criarCadUsuarioForm(Model model) {
        model.addAttribute("cadusuario", new CadUsuarios());
        return "cadusuarios-form";
    }

    @PostMapping("/salvar")
    public String salvarCadUsuario(@ModelAttribute("cadusuario") CadUsuarios cadusuario) {
        cadUsuariosService.salvar(cadusuario);
        return "redirect:/cadusuarios";
    }

    @GetMapping("/editar/{id}")
    public String editarCadUsuario(@PathVariable Long id, Model model) {
        CadUsuarios cadusuario = cadUsuariosService.buscarPorId(id);
        if (cadusuario == null) {
            return "redirect:/cadusuarios";
        }
        model.addAttribute("cadusuario", cadusuario);
        return "cadusuarios-form";
    }

    @GetMapping("/excluir/{id}")
    public String excluirCadUsuario(@PathVariable Long id) {
        cadUsuariosService.excluir(id);
        return "redirect:/cadusuarios";
    }
}
