package com.sistema.viagens.controller;

import com.sistema.viagens.model.Viagem;
import com.sistema.viagens.service.ViagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/viagens")
public class ViagemController {

    @Autowired
    private ViagemService viagemService;

    @PostMapping
    public ResponseEntity<Viagem> criarViagem(@RequestBody Viagem viagem) {
        return ResponseEntity.ok(viagemService.criarViagem(viagem));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Viagem>> buscarViagensPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(viagemService.buscarViagensPorCliente(clienteId));
    }
} 