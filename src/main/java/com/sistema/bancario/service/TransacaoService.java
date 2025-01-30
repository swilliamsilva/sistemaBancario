package com.sistema.bancario.service;

import com.sistema.bancario.model.Transacao;
import com.sistema.bancario.repository.TransacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.io.IOException;

@Service
public class TransacaoService {

    private static final Logger logger = LoggerFactory.getLogger(TransacaoService.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final float MARGIN = 50;
    private static final float FONT_SIZE = 12;
    private static final float LEADING = 14;
    private static final String[] HEADERS = {"Data/Hora", "Tipo", "Valor", "Saldo"};
    private static final float[] COLUMN_WIDTHS = {200, 100, 100, 100};

    @Autowired
    private TransacaoRepository transacaoRepository;

    public List<Transacao> buscarTransacoesPorPeriodo(String numeroConta, LocalDateTime inicio, LocalDateTime fim) {
        logger.debug("Buscando transações para conta {} entre {} e {}", numeroConta, inicio, fim);
        return transacaoRepository.findByContaNumeroContaAndDataHoraTransacaoBetween(
            numeroConta, inicio, fim);
    }

    public BigDecimal calcularTotalTransacoes(List<Transacao> transacoes) {
        logger.debug("Calculando o total de {} transações.", transacoes.size());
        return transacoes.stream()
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Transacao> buscarTransacoesPorTipo(Long contaId, String tipoTransacao) {
        logger.debug("Buscando transações do tipo {} para conta {}", tipoTransacao, contaId);
        return transacaoRepository.findByContaIdAndTipoTransacao(contaId, tipoTransacao);
    }

    public byte[] gerarExtratoPDF(String numeroConta, LocalDate dataInicial, LocalDate dataFinal) {
        logger.debug("Gerando PDF do extrato para conta {} entre {} e {}", 
            numeroConta, dataInicial, dataFinal);
            
        List<Transacao> transacoes = buscarTransacoesPorPeriodo(
            numeroConta,
            dataInicial.atStartOfDay(),
            dataFinal.atTime(23, 59, 59)
        );

        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            float pageHeight = page.getMediaBox().getHeight();
            float y = pageHeight - MARGIN;

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Cabeçalho
                desenharCabecalho(contentStream, numeroConta, dataInicial, dataFinal, y);
                y -= 5 * LEADING;

                // Cabeçalho da tabela
                desenharCabecalhoTabela(contentStream, y);
                y -= 2 * LEADING;

                // Dados das transações
                for (Transacao t : transacoes) {
                    if (y < MARGIN + LEADING) {
                        // Criar nova página
                        contentStream.close();
                        page = new PDPage(PDRectangle.A4);
                        document.addPage(page);
                        y = pageHeight - MARGIN;
                        
                        try (PDPageContentStream novaPageStream = new PDPageContentStream(document, page)) {
                            desenharCabecalhoTabela(novaPageStream, y);
                            y -= 2 * LEADING;
                            desenharLinhaTransacao(novaPageStream, t, y);
                        }
                    } else {
                        desenharLinhaTransacao(contentStream, t, y);
                    }
                    y -= LEADING;
                }
            }

            document.save(baos);
            logger.info("Extrato PDF gerado com {} transações", transacoes.size());
            return baos.toByteArray();
            
        } catch (Exception e) {
            logger.error("Erro ao gerar PDF do extrato: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao gerar PDF do extrato", e);
        }
    }

    private void desenharCabecalho(PDPageContentStream contentStream, String numeroConta, 
            LocalDate dataInicial, LocalDate dataFinal, float y) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, FONT_SIZE + 2);
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN, y);
        contentStream.showText("Extrato Bancário");
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.showText("Conta: " + numeroConta);
        contentStream.newLineAtOffset(0, -LEADING);
        contentStream.showText("Período: " + dataInicial + " a " + dataFinal);
        contentStream.endText();
    }

    private void desenharCabecalhoTabela(PDPageContentStream contentStream, float y) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, FONT_SIZE);
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN, y);
        for (int i = 0; i < HEADERS.length; i++) {
            contentStream.newLineAtOffset(i == 0 ? 0 : COLUMN_WIDTHS[i-1], 0);
            contentStream.showText(HEADERS[i]);
        }
        contentStream.endText();
    }

    private void desenharLinhaTransacao(PDPageContentStream contentStream, Transacao t, float y) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA, FONT_SIZE);
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN, y);
        
        String[] valores = {
            t.getDataHoraTransacao().format(DATE_FORMATTER),
            t.getTipoTransacao(),
            t.getValor().toString(),
            t.getSaldoResultante().toString()
        };

        for (int i = 0; i < valores.length; i++) {
            contentStream.newLineAtOffset(i == 0 ? 0 : COLUMN_WIDTHS[i-1], 0);
            contentStream.showText(valores[i]);
        }
        contentStream.endText();
    }
}
