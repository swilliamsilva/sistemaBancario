package com.sistema.bancario.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecuredEndpointController {

    @GetMapping("/secured-endpoint")
    public String securedEndpoint() {
        return "Acesso autorizado";
    }
}
