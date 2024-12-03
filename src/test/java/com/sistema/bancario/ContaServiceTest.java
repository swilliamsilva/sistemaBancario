package com.sistema.bancario;

import com.sistema.bancario.model.Conta;
import com.sistema.bancario.service.ContaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ContaServiceTest {

    @Autowired
    private ContaService contaService;

    private Conta conta;

 //   @BeforeEach
 //   public void setUp() {
        // Cria uma conta para ser usada nos testes
 //       conta = contaService.criarConta("Teste Titular", "12345678", BigDecimal.valueOf(500));
   // }

  //  @Test
  //  public void debitar_DeveLancarExcecaoQuandoSaldoInsuficiente() {
        // Tentando debitar um valor maior do que o saldo disponível
 //       assertThrows(IllegalArgumentException.class, () -> {
  //          contaService.debitar(conta.getId(), BigDecimal.valueOf(1000)); // Excede o saldo de 500
  //      }, "Saldo insuficiente para realizar a transação");
  //  }

 /*   @Test
    public void debitar_DeveReduzirSaldoQuandoSaldoSuficiente() {
        BigDecimal valorInicial = conta.getSaldo();
        BigDecimal valorDebitado = BigDecimal.valueOf(100);

        // Realizando o débito
        contaService.debitar(conta.getId(), valorDebitado);

        // Verificando se o saldo foi reduzido corretamente
        assertEquals(valorInicial.subtract(valorDebitado), conta.getSaldo(), "O saldo após o débito não foi atualizado corretamente");
    } */

  /*  @Test
    public void creditar_DeveLancarExcecaoQuandoValorInvalido() {
        // Tentando creditar um valor negativo
        assertThrows(IllegalArgumentException.class, () -> {
            contaService.creditar(conta.getId(), BigDecimal.valueOf(-100));
        }, "Valor de depósito deve ser maior que zero");
    }

/*   @Test
    public void creditar_DeveAjustarSaldoQuandoValorValido() {
        BigDecimal valorInicial = conta.getSaldo();
        BigDecimal valorCredito = BigDecimal.valueOf(200);

        // Realizando o crédito
        contaService.creditar(conta.getId(), valorCredito);

        // Verificando se o saldo foi ajustado corretamente
        assertEquals(valorInicial.add(valorCredito), conta.getSaldo(), "O saldo após o crédito não foi atualizado corretamente");
    } */
}
