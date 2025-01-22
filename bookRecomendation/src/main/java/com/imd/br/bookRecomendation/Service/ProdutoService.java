package com.imd.br.bookRecomendation.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imd.br.bookRecomendation.Model.Produto;
import com.imd.br.bookRecomendation.Repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import com.imd.br.bookRecomendation.Repository.ProdutoSpecification;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository lr;
    @Autowired
    private ObjectMapper objectMapper;

    public List<Produto> listarTodos() {
        return lr.findAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return lr.findById(id);
    }

    public Produto salvar(Produto produto) {
        return lr.save(produto);
    }

    public Produto atualizar(Long id, Produto produtoAtualizado) {
        Produto produtoExistente = lr.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produtoExistente.setTitulo(produtoAtualizado.getTitulo());
        produtoExistente.setGenero(produtoAtualizado.getGenero());
        produtoExistente.setSinopse(produtoAtualizado.getSinopse());
        produtoExistente.setAvaliacaoMedia(produtoAtualizado.getAvaliacaoMedia());

        return lr.save(produtoExistente);
    }

    public void deletar(Long id) {
        lr.deleteById(id);
    }

    public void salvarProdutos(String jsonResponse) throws Exception {
        JsonNode root = objectMapper.readTree(jsonResponse);
        JsonNode docs = root.path("docs");

        for (JsonNode doc : docs) {
            Produto produto = new Produto();
            int coverId = doc.path("cover_i").asInt();
            produto.setTitulo(doc.path("title").asText());
            produto.setAvaliacaoMedia(doc.path("ratings_average").asDouble());
            produto.setGenero(doc.path("subject").isArray() ? doc.path("subject").get(0).asText() : "Desconhecido");
            produto.setSinopse(doc.path("description").asText());
            produto.setLinkImage("https://covers.openlibrary.org/b/id/" + coverId + "-L.jpg");

            lr.save(produto);
        }
    }

    public List<Produto> filtrarProdutos(String titulo, String autor, String genero, Double avaliacaoMediaMin) {
        Specification<Produto> spec = Specification.where(ProdutoSpecification.filtroPorTitulo(titulo))
                .and(ProdutoSpecification.filtroPorAutor(autor))
                .and(ProdutoSpecification.filtroPorGenero(genero))
                .and(ProdutoSpecification.filtroPorAvaliacaoMedia(avaliacaoMediaMin));

        return lr.findAll(spec);
    }
}
