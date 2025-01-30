package com.sistema.bancario.controller;

import com.sistema.bancario.dto.LimiteDTO;
import com.sistema.bancario.model.Conta;
import com.sistema.bancario.service.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/saldo")
@Tag(name = "Saldo", description = "APIs para consulta de saldo")
public class SaldoController {

    @Autowired
    private ContaService contaService;

    /**
     * Endpoint chamado pelo componente de Saldo presente em várias telas
     * Atualizado automaticamente a cada 30 segundos
     */
    @Operation(summary = "Consultar saldo")
    @GetMapping("/{numeroConta}")
    public ResponseEntity<BigDecimal> consultarSaldo(
            @Parameter(description = "Número da conta") @PathVariable String numeroConta) {
        Conta conta = contaService.buscarPorNumero(numeroConta);
        return ResponseEntity.ok(conta.getSaldo());
    }

    /**
     * Endpoint chamado pela tela de Limite (/views/limite.xhtml)
     * Exibe informações do cheque especial
     */
    @Operation(summary = "Consultar limite")
    @GetMapping("/{numeroConta}/limite")
    public ResponseEntity<LimiteDTO> consultarLimite(
            @Parameter(description = "Número da conta") @PathVariable String numeroConta) {
        Conta conta = contaService.buscarPorNumero(numeroConta);
        
        LimiteDTO limite = new LimiteDTO();
        limite.setLimiteTotal(conta.getLimiteEspecial());
        limite.setLimiteUtilizado(conta.getSaldoEspecial());
        limite.setLimiteDisponivel(conta.getLimiteEspecial().subtract(conta.getSaldoEspecial()));
        
        return ResponseEntity.ok(limite);
    }
} 