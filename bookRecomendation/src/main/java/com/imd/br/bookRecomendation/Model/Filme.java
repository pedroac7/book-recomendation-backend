package com.imd.br.bookRecomendation.Model;

import jakarta.persistence.Entity;

@Entity
public class Filme extends Produto {
    private String diretor;
    private Integer duracao; // em minutos
    private Integer ano;
    private String idioma;


    public Filme() {
        // Construtor padrão obrigatório
    }


    // Getters e Setters
    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
}
