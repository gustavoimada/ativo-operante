package com.example.projeto2bi_fciii.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

// 1) Relacionamento N:N com Tipo (gera uma classe associativa com elemento extra)
    // NAO PRECISA DE UMA LISTA DE DENUNCIAS AQUI

@Entity
@Table(name="orgaos")
public class Orgao
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="org_id")
    private Long id;

    @Column(name="org_nome")
    private String nome;

    // construtores

    public Orgao()
    {
        this(0L, "");
    }

    public Orgao(Long id, String nome)
    {
        this.id = id;
        this.nome = nome;
    }

    public Orgao(String nome)
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
