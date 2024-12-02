package com.sistema.bancario.model;

import javax.persistence.*;

@Entity
@Table(name = "cad_usuarios")
public class CadUsuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "conta_cliente", nullable = false, unique = true, length = 50)
    private String contaCliente;

    @Column(name = "nome_cliente", nullable = false, length = 255)
    private String nomeCliente;

    @Column(nullable = false, length = 255)
    private String senha;

    @Column(nullable = false, unique = true, length = 50)
    private String usuario;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContaCliente() {
        return contaCliente;
    }

    public void setContaCliente(String contaCliente) {
        this.contaCliente = contaCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
