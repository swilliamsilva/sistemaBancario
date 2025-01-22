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

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
public class MovimentacaoController {

    private static final Logger logger = LoggerFactory.getLogger(MovimentacaoController.class);

    @Autowired
    private ContaService contaService;

    @Autowired
    private TransacaoRepository transacaoRepository;

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
