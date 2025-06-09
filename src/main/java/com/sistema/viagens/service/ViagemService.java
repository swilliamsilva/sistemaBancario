package com.sistema.viagens.service;

import com.sistema.viagens.model.Viagem;
import com.sistema.viagens.repository.ViagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ViagemService {
    
    @Autowired
    private ViagemRepository viagemRepository;
    
    public Viagem criarViagem(Viagem viagem) {
        // Validações e regras de negócio
        return viagemRepository.save(viagem);
    }
    
    public List<Viagem> buscarViagensPorCliente(Long clienteId) {
        return viagemRepository.findByClienteId(clienteId);
    }
    
    public List<Viagem> buscarViagensPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return viagemRepository.findByDataPartidaBetween(inicio, fim);
    }
} 