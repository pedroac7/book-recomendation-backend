package com.imd.br.bookRecomendation.Service;

import com.imd.br.bookRecomendation.Model.Historico;
import com.imd.br.bookRecomendation.Model.Produto;
import com.imd.br.bookRecomendation.Model.Usuario;
import com.imd.br.bookRecomendation.Repository.HistoricoProdutosRepository;
import com.imd.br.bookRecomendation.Repository.ProdutoRepository;
import com.imd.br.bookRecomendation.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricoService {

    @Autowired
    private HistoricoProdutosRepository hr;

    @Autowired
    private ProdutoRepository lr;

    @Autowired
    private UsuarioRepository ur;

    public Historico adicionar(Long usuarioId, Long produtoId) {

        Usuario usuario = ur.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Produto produto = lr.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Adiciona o registro ao histórico
        Historico historico = new Historico();
        historico.setUsuario(usuario);
        historico.setProduto(produto);

        return hr.save(historico);
    }

    public List<Historico> buscarPorUsuario(Long usuarioId) {
        return hr.findByUsuarioId(usuarioId);
    }
}
