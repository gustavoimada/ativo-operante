package com.example.projeto2bi_fciii.entities;

import jakarta.persistence.*;

// 1) Relacionamento 1:N com Denuncia
    // recebe um objeto do tipo Denuncia

@Entity
@Table(name="imagem")
public class Imagem
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="img_id")
    private Long id;

    @Column(name="img_caminho")
    private String caminho;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="den_id")
    private Denuncia denuncia;

    // construtores

    public Imagem()
    {
        this(0L, "", null);
    }


    public Imagem(Long id, String caminho, Denuncia denuncia)
    {
        this.id = id;
        this.caminho = caminho;
        this.denuncia = denuncia;
    }

    public Imagem(String caminho, Denuncia denuncia)
    {
        this(0L, caminho, denuncia);
    }

    // gets e sets

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    // não faz get pro 1

    public void setDenuncia(Denuncia denuncia) {
        this.denuncia = denuncia;
    }
}
