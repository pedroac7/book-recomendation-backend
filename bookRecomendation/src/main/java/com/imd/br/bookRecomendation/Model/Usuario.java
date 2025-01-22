package com.imd.br.bookRecomendation.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;

    private String senha;
    private String generoPreferido;

    @ManyToMany
    @JoinTable(name = "usuario_produtos_favoritos", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "produto_id"))
    private List<Produto> produtosFavoritos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Historico> historicoProdutos;

    @ManyToMany
    @JoinTable(name = "usuario_produtos_desejados", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "produto_id"))
    private List<Produto> produtosDesejados;

    // Getters e Setters

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Produto> getProdutosFavoritos() {
        return produtosFavoritos;
    }

    public void setProdutosFavoritos(List<Produto> produtosFavoritos) {
        this.produtosFavoritos = produtosFavoritos;
    }

    public List<Produto> getProdutosDesejados() {
        return produtosDesejados;
    }

    public void setProdutosDesejados(List<Produto> produtosDesejados) {
        this.produtosDesejados = produtosDesejados;
    }

    public List<Historico> getHistoricoProdutos() {
        return historicoProdutos;
    }

    public void setHistoricoProdutos(List<Historico> historicoProdutos) {
        this.historicoProdutos = historicoProdutos;
    }

    public String getGeneroPreferido() {
        return generoPreferido;
    }

    public void setGeneroPreferido(String generoPreferido) {
        this.generoPreferido = generoPreferido;
    }
}