package com.example.projeto2bi_fciii.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// 1) Relacionamento de N:1 com a tabela Usuário (relacionamento simples sem multiplicidade)
    // recebe um objeto do tipo Usuario

// 2) Relacionamento de 1:1 com a tabela Feedback
    // recebe um objeto do tipo Feedback

// 3) É a tabela intermediaria do relacionamento N:N de tipo e orgao, portanto, recebe:

    // 3.1) recebe um objeto do tipo Tipo
    // 3.2) recebe um objeto do tipo Orgao

// 4) Relacionamento de 1:N com a tabela Imagem (composicao)
    // recebe uma lista de imagens

@Entity
@Table(name="denuncia")
public class Denuncia
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "den_id")
    private Long id;

    @Column(name = "den_titulo")
    private String titulo;

    @Column(name = "den_texto")
    private String texto;

    @Column(name = "den_urgencia")
    private int urgencia;

    @Column(name = "den_data")
    private LocalDate data;

    // 1)
    @ManyToOne
    @JoinColumn(name="usu_id", nullable=false)
    private Usuario usuario;

    // 2)
    @OneToOne(mappedBy = "denuncia", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Feedback feedback;

    // 3.1)
    @ManyToOne
    @JoinColumn(name = "org_id", referencedColumnName = "org_id")
    private Orgao orgao;

    // 3.2)
    @ManyToOne
    @JoinColumn(name = "tip_id", referencedColumnName = "tip_id")
    private Tipo tipo;

    // 4)
    @OneToMany(mappedBy = "denuncia", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Imagem> imagens = new ArrayList<>();

    public void addImagem(Imagem novaImagem)
    {
        novaImagem.setDenuncia(this);
        this.imagens.add(novaImagem);
    }

    // construtores

    public Denuncia()
    {
        this(0L, "", "", 0, null, null, null, null, null);
    }

    public Denuncia(Long id, String titulo, String texto, int urgencia, LocalDate data, Usuario usuario, Feedback feedback, Tipo tipo, Orgao orgao)
    {
        this.id = id;
        this.titulo = titulo;
        this.texto = texto;
        this.urgencia = urgencia;
        this.data = data;
        this.usuario = usuario;
        this.feedback = feedback;
        this.tipo = tipo;
        this.orgao = orgao;
    }

    public Denuncia(String titulo, String texto, int urgencia, LocalDate data, Usuario usuario, Feedback feedback, Tipo tipo, Orgao orgao)
    {
        this(0L, titulo, texto, urgencia, data, usuario, feedback, tipo, orgao);
    }

    // gets e sets padrao da classe

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getUrgencia() {
        return urgencia;
    }

    public void setUrgencia(int urgencia) {
        this.urgencia = urgencia;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    // gets e sets por conta do 1)

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    // gets e sets por conta do 2)

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    // gets e sets por cotna do 3.1 e do 3.2

    public Orgao getOrgao() {
        return orgao;
    }

    public void setOrgao(Orgao orgao) {
        this.orgao = orgao;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    // gets e sets por conta do 4

    public List<Imagem> getImagens() {
        return imagens;
    }

    public void setImagens(List<Imagem> imagens) {
        this.imagens = imagens;
    }
}
