package com.example.projeto2bi_fciii.entities;

import jakarta.persistence.*;

// 1) relacionamento de 1:1 com a tabela Denuncia

@Entity
@Table(name = "feedback")
public class Feedback
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fee_id")
    private Long id;

    @Column(name = "fee_texto")
    private String texto;

    // 1)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "den_id")
    private Denuncia denuncia;

    // construtores

    public Feedback()
    {
        this(0L, "", null);
    }

    public Feedback(Long id, String texto, Denuncia denuncia)
    {
        this.id = id;
        this.texto = texto;
        this.denuncia = denuncia;
    }

    public Feedback(String texto, Denuncia denuncia)
    {
        this(0L, texto, denuncia);
    }

    // gets e sets padrao da classe

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    // set por conta do 1)
    // nao faco get pro 1) (se nao gera problema de recursividade)

    public void setDenuncia(Denuncia denuncia) {
        this.denuncia = denuncia;
    }
}
