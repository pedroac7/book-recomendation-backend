package com.imd.br.bookRecomendation.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imd.br.bookRecomendation.Model.Filme;
import com.imd.br.bookRecomendation.Repository.FilmeRepository;
import com.imd.br.bookRecomendation.Repository.FilmeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;

@Service
public class FilmeService extends ProdutoService {

    @Autowired
    private FilmeRepository filmeRepository; // Repositório específico de Filme

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TMDbService tmdbService; // Injetando o TMDbService


    @Override
    public void salvarProdutos(String jsonResponse) throws Exception {
        JsonNode root = objectMapper.readTree(jsonResponse);
        JsonNode results = root.path("results");

        for (JsonNode result : results) {
            Filme filme = new Filme();
            filme.setTitulo(result.path("title").asText());
            filme.setSinopse(result.path("overview").asText());
            filme.setGenero("Filme");
            filme.setAvaliacaoMedia(result.path("vote_average").asDouble());
            filme.setLinkImage("https://image.tmdb.org/t/p/w500" + result.path("poster_path").asText());

            // Ajuste para ano
            String releaseDate = result.path("release_date").asText();
            if (!releaseDate.isEmpty()) {
                filme.setAno(Integer.parseInt(releaseDate.split("-")[0]));
            } else {
                filme.setAno(null);
            }

            filme.setIdioma(result.path("original_language").asText());

            // Buscar mais detalhes sobre o filme, como duração e diretor
            int movieId = result.path("id").asInt();
            String detalhesFilmeJson = tmdbService.buscarDetalhesFilme(movieId);
            JsonNode detalhesFilme = objectMapper.readTree(detalhesFilmeJson);

            filme.setDuracao(detalhesFilme.path("runtime").asInt(0));  // Duração em minutos
            filme.setDiretor(getDiretor(detalhesFilme)); // Chamando o método para buscar o diretor

            filmeRepository.save(filme);
        }
    }

    // Método para buscar o nome do diretor
    private String getDiretor(JsonNode detalhesFilme) {
        JsonNode crew = detalhesFilme.path("credits").path("crew");
        for (JsonNode member : crew) {
            if ("Director".equals(member.path("job").asText())) {
                return member.path("name").asText();
            }
        }
        return ""; // Se não encontrar o diretor, retorna uma string vazia
    }

    public List<Filme> filtrarProdutos(String titulo, String genero, Double avaliacaoMediaMin,
                                       String diretor, Integer ano, Integer duracao, String idioma) {
        Specification<Filme> spec = Specification.where(FilmeSpecification.filtroPorTitulo(titulo))
                .and(FilmeSpecification.filtroPorGenero(genero))
                .and(FilmeSpecification.filtroPorAvaliacaoMedia(avaliacaoMediaMin))
                .and(FilmeSpecification.filtroPorDiretor(diretor))
                .and(FilmeSpecification.filtroPorAno(ano))
                .and(FilmeSpecification.filtroPorDuracao(duracao))
                .and(FilmeSpecification.filtroPorIdioma(idioma));

        return filmeRepository.findAll(spec);
    }
}
