package com.imd.br.bookRecomendation.Repository;

import com.imd.br.bookRecomendation.Model.Historico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoProdutosRepository extends JpaRepository<Historico, Long> {
    List<Historico> findByUsuarioId(Long usuarioId);
}
