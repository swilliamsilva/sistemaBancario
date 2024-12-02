package com.sistema.bancario.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Import para o Model
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam; // Import para o RequestParam

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("error", "Usuário ou senha inválidos!");
        }
        if (logout != null) {
            model.addAttribute("logout", "Logout realizado com sucesso.");
        }
        return "login"; // Retorna para a página de login
    }
}
