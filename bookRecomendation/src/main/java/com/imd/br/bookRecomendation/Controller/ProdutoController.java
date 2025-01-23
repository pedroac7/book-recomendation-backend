package com.imd.br.bookRecomendation.Controller;

import com.imd.br.bookRecomendation.Api.ImportadorProdutos;
import com.imd.br.bookRecomendation.Model.Produto;
import com.imd.br.bookRecomendation.Service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService ls;
    @Autowired
    private ImportadorProdutos il;

    @GetMapping("/filtro")
    public List<Produto> filtrarProdutos(@RequestParam(required = false) String titulo,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) Double avaliacaoMediaMin) {
        return ls.filtrarProdutos(titulo, autor, genero, avaliacaoMediaMin);
    }

    @GetMapping
    public List<Produto> listarTodos() {
        return ls.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Produto> buscarPorId(@PathVariable Long id) {
        return ls.buscarPorId(id);
    }

    @PostMapping
    public Produto salvar(@RequestBody Produto produto) {
        return ls.salvar(produto);
    }

    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        return ls.atualizar(id, produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        ls.deletar(id);
    }

    @PostMapping("/importar")
    public ResponseEntity<String> importarProdutos(@RequestParam String query) {
        il.importarProdutos(query);  // Passando a query para o serviço de importação
        return ResponseEntity.ok("Importação de filmes iniciada para a query: " + query);
    }


}
