package com.sistema.bancario.service;

import com.sistema.bancario.model.Conta;
import com.sistema.bancario.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Transactional
    public void transferir(Long idContaOrigem, Long idContaDestino, Double valor) {
        Conta contaOrigem = contaRepository.findById(idContaOrigem)
            .orElseThrow(() -> new RuntimeException("Conta de origem não encontrada: " + idContaOrigem));
        Conta contaDestino = contaRepository.findById(idContaDestino)
            .orElseThrow(() -> new RuntimeException("Conta de destino não encontrada: " + idContaDestino));

        if (contaOrigem.getSaldo() < valor) {
            throw new RuntimeException("Saldo insuficiente na conta de origem: " + idContaOrigem);
        }

        // Atualizar saldos
        contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
        contaDestino.setSaldo(contaDestino.getSaldo() + valor);

        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);
    }
}
