package com.sistema.bancario.controller;

import com.sistema.bancario.model.Conta;
import com.sistema.bancario.model.Transacao;
import com.sistema.bancario.repository.TransacaoRepository;
import com.sistema.bancario.service.ContaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/api/movimentacao")
@Tag(name = "Movimentação", description = "APIs para movimentações bancárias")
public class MovimentacaoController {

    private static final Logger logger = LoggerFactory.getLogger(MovimentacaoController.class);

    @Autowired
    private ContaService contaService;

    @Autowired
    private TransacaoRepository transacaoRepository;

    /**
     * Endpoint chamado pela tela de Saque (/views/saque.xhtml)
     * Botão "Realizar Saque" aciona esta operação
     */
    @Operation(summary = "Realizar saque", 
              description = "Realiza saque em conta corrente. Chamado pela tela de Saque.")
    @PostMapping("/saque")
    public ResponseEntity<Boolean> realizarSaque(
            @Parameter(description = "Número da conta") @RequestParam String numeroConta,
            @Parameter(description = "Valor do saque") @RequestParam BigDecimal valor) {
        return ResponseEntity.ok(contaService.realizarSaque(numeroConta, valor));
    }

    /**
     * Endpoint chamado pela tela de Depósito (/views/deposito.xhtml)
     * Botão "Confirmar Depósito" aciona esta operação
     */
    @Operation(summary = "Realizar depósito", 
              description = "Realiza depósito em conta corrente. Chamado pela tela de Depósito.")
    @PostMapping("/deposito")
    public ResponseEntity<Boolean> realizarDeposito(
            @Parameter(description = "Número da conta") @RequestParam String numeroConta,
            @Parameter(description = "Valor do depósito") @RequestParam BigDecimal valor) {
        return ResponseEntity.ok(contaService.realizarDeposito(numeroConta, valor));
    }

    /**
     * Endpoint chamado pela tela de Transferência (/views/transferencia.xhtml)
     * Botão "Transferir" aciona esta operação
     */
    @Operation(summary = "Realizar transferência", 
              description = "Realiza transferência entre contas. Chamado pela tela de Transferência.")
    @PostMapping("/transferencia")
    public ResponseEntity<Boolean> realizarTransferencia(
            @Parameter(description = "Conta de origem") @RequestParam String contaOrigem,
            @Parameter(description = "Conta de destino") @RequestParam String contaDestino,
            @Parameter(description = "Valor da transferência") @RequestParam BigDecimal valor) {
        return ResponseEntity.ok(contaService.realizarTransferencia(contaOrigem, contaDestino, valor));
    }

    @PostMapping("/movimentacao")
    public String movimentar(
            @RequestParam("contaId") Long contaId,
            @RequestParam("tipoTransacao") String tipoTransacao,
            @RequestParam(value = "valor", required = false) Double valor,
            Model model) {

        // Verificar se os parâmetros obrigatórios estão presentes
        if (contaId == null || tipoTransacao == null) {
            model.addAttribute("mensagemErro", "Dados obrigatórios não fornecidos.");
            logger.error("Erro: Dados obrigatórios não fornecidos.");
            return "movimentacaoDeConta";
        }

        try {
            Conta conta = contaService.buscarContaPorId(contaId);
            if (conta == null) {
                throw new IllegalArgumentException("Conta não encontrada.");
            }

            // Instanciar a transação
            Transacao transacao = new Transacao();
            transacao.setConta(conta);
            transacao.setDataHoraTransacao(LocalDateTime.now());

            switch (tipoTransacao.toUpperCase()) {
                case "DEPOSITO":
                    if (valor == null || valor <= 0) {
                        throw new IllegalArgumentException("Valor para depósito inválido.");
                    }
                    contaService.creditar(contaId, BigDecimal.valueOf(valor));
                    transacao.setTipoTransacao("DEPOSITO");
                    transacao.setValor(BigDecimal.valueOf(valor));
                    transacao.setValorUsadoEspecial(BigDecimal.ZERO); // Se não houver uso do especial
                    break;

                case "SAQUE":
                    if (valor == null || valor <= 0) {
                        throw new IllegalArgumentException("Valor para saque inválido.");
                    }
                    contaService.debitar(contaId, BigDecimal.valueOf(valor));
                    transacao.setTipoTransacao("SAQUE");
                    transacao.setValor(BigDecimal.valueOf(valor));
                    transacao.setValorUsadoEspecial(BigDecimal.ZERO); // Se não houver uso do especial
                    break;

                default:
                    throw new IllegalArgumentException("Tipo de transação inválido.");
            }

            transacaoRepository.save(transacao);

        } catch (Exception e) {
            model.addAttribute("mensagemErro", "Erro ao processar a transação: " + e.getMessage());
            return "movimentacaoDeConta";
        }

        return "redirect:/listar-contas";
    }
}
