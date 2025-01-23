package com.imd.br.bookRecomendation.Repository;

import com.imd.br.bookRecomendation.Model.Filme;
import org.springframework.data.jpa.domain.Specification;

public class FilmeSpecification {

    public static Specification<Filme> filtroPorTitulo(String titulo) {
        return (root, query, criteriaBuilder) -> titulo == null ? null
                : criteriaBuilder.like(criteriaBuilder.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%");
    }

    public static Specification<Filme> filtroPorGenero(String genero) {
        return (root, query, criteriaBuilder) -> genero == null ? null
                : criteriaBuilder.like(criteriaBuilder.lower(root.get("genero")), "%" + genero.toLowerCase() + "%");
    }

    public static Specification<Filme> filtroPorAvaliacaoMedia(Double avaliacaoMediaMin) {
        return (root, query, criteriaBuilder) -> avaliacaoMediaMin == null ? null
                : criteriaBuilder.greaterThanOrEqualTo(root.get("avaliacaoMedia"), avaliacaoMediaMin);
    }

    public static Specification<Filme> filtroPorDiretor(String diretor) {
        return (root, query, criteriaBuilder) -> diretor == null ? null
                : criteriaBuilder.like(criteriaBuilder.lower(root.get("diretor")), "%" + diretor.toLowerCase() + "%");
    }

    public static Specification<Filme> filtroPorAno(Integer ano) {
        return (root, query, criteriaBuilder) -> ano == null ? null
                : criteriaBuilder.equal(root.get("ano"), ano);
    }

    public static Specification<Filme> filtroPorDuracao(Integer duracao) {
        return (root, query, criteriaBuilder) -> duracao == null ? null
                : criteriaBuilder.greaterThanOrEqualTo(root.get("duracao"), duracao);
    }

    public static Specification<Filme> filtroPorIdioma(String idioma) {
        return (root, query, criteriaBuilder) -> idioma == null ? null
                : criteriaBuilder.like(criteriaBuilder.lower(root.get("idioma")), "%" + idioma.toLowerCase() + "%");
    }
}
