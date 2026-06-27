package com.example.projeto2bi_fciii.entities;

import jakarta.persistence.*;

// 1) Relacionamento N:N com Orgao (gera uma classe associativa com elemento extra)
    // NAO PRECISA DE UMA LISTA DE DENUNCIAS AQUI

@Entity
@Table(name="tipo")
public class Tipo
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tip_id")
    private Long id;

    @Column(name="tip_nome")
    private String nome;

    // construtores

    public Tipo()
    {
        this(0L, "");
    }

    public Tipo(Long id, String nome)
    {
        this.id = id;
        this.nome = nome;
    }

    public Tipo(String nome)
    {
        this(0L, nome);
    }

    // gets e sets

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
