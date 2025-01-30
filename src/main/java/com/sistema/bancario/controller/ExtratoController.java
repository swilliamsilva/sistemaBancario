package com.sistema.bancario.controller;

import com.sistema.bancario.dto.MovimentacaoDTO;
import com.sistema.bancario.model.Transacao;
import com.sistema.bancario.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/extrato")
@Tag(name = "Extrato", description = "APIs para consulta de extratos")
public class ExtratoController {

    @Autowired
    private TransacaoService transacaoService;

    @Operation(summary = "Consultar extrato")
    @GetMapping("/{numeroConta}")
    public ResponseEntity<List<MovimentacaoDTO>> consultarExtrato(
            @Parameter(description = "Número da conta") @PathVariable String numeroConta,
            @Parameter(description = "Data inicial") @RequestParam LocalDate dataInicial,
            @Parameter(description = "Data final") @RequestParam LocalDate dataFinal) {
        
        List<Transacao> transacoes = transacaoService.buscarTransacoesPorPeriodo(
            numeroConta, 
            dataInicial.atStartOfDay(), 
            dataFinal.atTime(23, 59, 59)
        );
        
        List<MovimentacaoDTO> movimentacoes = transacoes.stream()
            .map(this::converterParaDTO)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(movimentacoes);
    }

    @Operation(summary = "Gerar extrato PDF")
    @GetMapping("/{numeroConta}/pdf")
    public ResponseEntity<byte[]> gerarExtratoPDF(
            @Parameter(description = "Número da conta") @PathVariable String numeroConta,
            @Parameter(description = "Data inicial") @RequestParam LocalDate dataInicial,
            @Parameter(description = "Data final") @RequestParam LocalDate dataFinal) {
        
        byte[] pdf = transacaoService.gerarExtratoPDF(numeroConta, dataInicial, dataFinal);
        
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=extrato.pdf")
            .body(pdf);
    }

    private MovimentacaoDTO converterParaDTO(Transacao transacao) {
        MovimentacaoDTO dto = new MovimentacaoDTO();
        dto.setTipo(transacao.getTipoTransacao());
        dto.setValor(transacao.getValor());
        dto.setDataHora(transacao.getDataHoraTransacao());
        dto.setSaldoResultante(transacao.getSaldoResultante());
        return dto;
    }
}
