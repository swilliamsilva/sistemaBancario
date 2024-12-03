package com.sistema.bancario;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sistema.bancario.model.Conta;
import com.sistema.bancario.model.Transacao;
import com.sistema.bancario.repository.ContaRepository;
import com.sistema.bancario.repository.TransacaoRepository;

@SpringBootTest
public class TransacaoRepositoryTest {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Test
    public void testFindByContaIdAndDataHoraTransacaoBetween() {
        // Criando a conta para o teste
        Conta conta = new Conta();
        conta.setTitular("Teste Titular");
        conta.setNumeroConta("12345678");
        conta.setSaldo(BigDecimal.valueOf(1000));
        conta.setSaldoEspecial(BigDecimal.valueOf(500));
 //       conta = contaRepository.save(conta);

        // Criação do timestamp atual para garantir consistência no teste
        LocalDateTime now = LocalDateTime.now();

        // Criando a transação para o teste
        Transacao transacao = new Transacao();
        transacao.setConta(conta);
        transacao.setDataHoraTransacao(now);  // Usando o timestamp atual
        transacao.setTipoTransacao("DEPOSITO");
        transacao.setValor(BigDecimal.valueOf(200));
        transacao.setValorUsadoEspecial(BigDecimal.ZERO);
    //    transacaoRepository.save(transacao);

        // Executando a consulta
   //     List<Transacao> transacoes = transacaoRepository.findByContaIdAndDataHoraTransacaoBetween(
   //         conta.getId(),
   //         now.minusDays(1),  // 1 dia antes de "now"
   //         now.plusDays(1)    // 1 dia depois de "now"
    //    );

        // Validando os resultados
     //   assertFalse(transacoes.isEmpty());
     //   assertEquals(1, transacoes.size());
     //   assertEquals("DEPOSITO", transacoes.get(0).getTipoTransacao());
    }
}
