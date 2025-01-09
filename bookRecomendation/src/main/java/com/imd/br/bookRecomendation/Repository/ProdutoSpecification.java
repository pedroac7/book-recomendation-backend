package com.imd.br.bookRecomendation.Repository;

import com.imd.br.bookRecomendation.Model.Produto;
import org.springframework.data.jpa.domain.Specification;

public class ProdutoSpecification {
    public static Specification<Produto> filtroPorTitulo(String titulo) {
        return (root, query, criteriaBuilder) -> titulo == null ? null
                : criteriaBuilder.like(criteriaBuilder.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%");
    }

    public static Specification<Produto> filtroPorAutor(String autor) {
        return (root, query, criteriaBuilder) -> autor == null ? null
                : criteriaBuilder.like(criteriaBuilder.lower(root.get("autor")), "%" + autor.toLowerCase() + "%");
    }

    public static Specification<Produto> filtroPorGenero(String genero) {
        return (root, query, criteriaBuilder) -> genero == null ? null
                : criteriaBuilder.like(criteriaBuilder.lower(root.get("genero")), "%" + genero.toLowerCase() + "%");
    }

    public static Specification<Produto> filtroPorAvaliacaoMedia(Double avaliacaoMediaMin) {
        return (root, query, criteriaBuilder) -> avaliacaoMediaMin == null ? null
                : criteriaBuilder.greaterThanOrEqualTo(root.get("avaliacaoMedia"), avaliacaoMediaMin);
    }
}
