package com.imd.br.bookRecomendation.Repository;

import com.imd.br.bookRecomendation.Model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FilmeRepository extends JpaRepository<Filme, Long>, JpaSpecificationExecutor<Filme> {
}
