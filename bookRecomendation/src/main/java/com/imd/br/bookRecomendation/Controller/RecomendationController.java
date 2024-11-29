package com.imd.br.bookRecomendation.Controller;

import com.imd.br.bookRecomendation.Model.Livro;
import com.imd.br.bookRecomendation.Service.RecomendationService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recomendation")
public class RecomendationController {

    private final ChatClient chatClient;

    @Autowired
    private RecomendationService rs;

    private String system = "Responda de forma curta, clara e objetiva, você é um recomendador de livros";

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
        return this.chatClient.prompt()
                .system(system)
                .user(message)
                .call()
                .content();
    }

    @GetMapping("/genero/{id}")
    public String recomendationByGenero(@PathVariable Long id) {

        String message = rs.getMessageByGenero(id);
        return this.chatClient.prompt()
                .system(system)
                .user(message)
                .call()
                .content();
    }

    @GetMapping("/livro/{id}")
    public String recomendationByLivro(@PathVariable Long id){

        String message = rs.getMessageByUltimoLivro(id);
        return this.chatClient.prompt()
                .system(system)
                .user(message)
                .call()
                .content();
    }

}
