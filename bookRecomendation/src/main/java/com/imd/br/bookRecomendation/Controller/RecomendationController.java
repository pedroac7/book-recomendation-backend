package com.imd.br.bookRecomendation.Controller;

import com.imd.br.bookRecomendation.Model.Livro;
import com.imd.br.bookRecomendation.Model.Usuario;
import com.imd.br.bookRecomendation.Repository.UsuarioRepository;
import com.imd.br.bookRecomendation.Service.RecomendationService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/recomendation")
public class RecomendationController {

    private final ChatClient chatClient;

    @Autowired
    private RecomendationService rs;

    public RecomendationController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultOptions(ChatOptionsBuilder
                        .builder()
                        .withModel("gpt-4o-mini")
                        .build())
                .build();
    }

    @GetMapping
    public String recomendation(String message) {
        String system = "Responda de forma curta, clara e objetiva, você é um recomendador de livros";
        return this.chatClient.prompt()
                .system(system)
                .user(message)
                .call()
                .content();
    }

    @GetMapping("/genero/{id}")
    public String recomendationByGenero(@PathVariable Long id) {
        String system = "Responda de forma curta, clara e objetiva, você é um recomendador de livros";

        String genero = rs.getGeneroById(id);

        String message = "Recomende livros do mesmo genero preferido do usuario: " + genero;
        return this.chatClient.prompt()
                .system(system)
                .user(message)
                .call()
                .content();
    }

    @GetMapping("/livro/{id}")
    public String recomendationByLivro(@PathVariable Long id){
        String system = "Responda de forma curta, clara e objetiva, você é um recomendador de livros";

        Livro livro = rs.getUltimoLivro(id);

        String message = "Recomende livros semelhantes ao ultimo livro lido pelo usuario: " + livro.getTitulo();
        return this.chatClient.prompt()
                .system(system)
                .user(message)
                .call()
                .content();
    }

}
