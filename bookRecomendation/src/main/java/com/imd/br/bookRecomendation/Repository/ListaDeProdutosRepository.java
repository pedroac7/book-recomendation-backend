package com.imd.br.bookRecomendation.Repository;

import com.imd.br.bookRecomendation.Model.ListaDeProdutos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListaDeProdutosRepository extends JpaRepository<ListaDeProdutos, Long> {
}