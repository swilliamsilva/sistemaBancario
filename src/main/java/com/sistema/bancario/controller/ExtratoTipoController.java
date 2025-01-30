package com.sistema.bancario.controller;

import com.sistema.bancario.model.Transacao;
import com.sistema.bancario.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/extrato-tipo")
public class ExtratoTipoController {

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping("/{contaId}/{tipo}")
    public ResponseEntity<List<Transacao>> buscarPorTipo(
            @PathVariable Long contaId,
            @PathVariable String tipo) {
        List<Transacao> transacoes = transacaoService.buscarTransacoesPorTipo(contaId, tipo);
        return ResponseEntity.ok(transacoes);
    }
}
