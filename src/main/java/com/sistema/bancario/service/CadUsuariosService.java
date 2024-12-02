package com.sistema.bancario.service;

import com.sistema.bancario.model.CadUsuarios;
import com.sistema.bancario.repository.CadUsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadUsuariosService {

    @Autowired
    private CadUsuariosRepository cadUsuariosRepository;

    public List<CadUsuarios> buscarTodos() {
        return cadUsuariosRepository.findAll();
    }

    public CadUsuarios buscarPorId(Long id) {
        return cadUsuariosRepository.findById(id).orElse(null);
    }

    public void salvar(CadUsuarios cadusuario) {
        cadUsuariosRepository.save(cadusuario);
    }

    public void excluir(Long id) {
        cadUsuariosRepository.deleteById(id);
    }
}
