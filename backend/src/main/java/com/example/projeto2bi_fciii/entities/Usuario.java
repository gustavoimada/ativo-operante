package com.example.projeto2bi_fciii.entities;

import jakarta.persistence.*;

// relacionamento de 1:N com a tabela Denuncia
    // nao muda nada aqui em relacao isso porque quem recebe o objeto eh na tabela Denuncia

@Entity
@Table(name="usuario")
public class Usuario
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="usu_id")
    private Long id;

    @Column(name="usu_cpf")
    private Long cpf;

    @Column(name="usu_email")
    private String email;

    @Column(name="usu_senha")
    private int senha;

    @Column(name="usu_nivel")
    private int nivel;

    // construtores

    public Usuario()
    {
        this(0L, 0L, "", 0, 0);
    }

    public Usuario(Long id, Long cpf, String email, int senha, int nivel)
    {
        this.id = id;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.nivel = nivel;
    }

    public Usuario(Long cpf, String email, int senha, int nivel)
    {
        this(0L, cpf, email, senha, nivel);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSenha() {
        return senha;
    }

    public void setSenha(int senha) {
        this.senha = senha;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}
