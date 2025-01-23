package com.imd.br.bookRecomendation.Service;

import com.imd.br.bookRecomendation.Model.Filme;
import com.imd.br.bookRecomendation.Model.Historico;
import com.imd.br.bookRecomendation.Model.Usuario;
import com.imd.br.bookRecomendation.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecomendationServiceFilmes extends RecomendationService {

    @Autowired
    private UsuarioRepository ur;

    @Autowired
    private HistoricoService hs;

    @Override
    public String getMessageByGenero(Long id) {
        Optional<Usuario> usuario = ur.findById(id);

        // Obtem o gênero preferido do usuário
        String genero = usuario.map(Usuario::getGeneroPreferido).orElse("Drama");

        // Monta a mensagem de recomendação com base no gênero
        String message = "Recomende filmes do gênero preferido do usuário: " + genero;

        return message;
    }

    @Override
    public String getMessageByUltimoProduto(Long id) {

        // Busca o histórico de consumo do usuário
        List<Historico> historico = hs.buscarPorUsuario(id);

        // Verifica se o histórico contém registros
        if (historico.isEmpty()) {
            return "O usuário ainda não consumiu nenhum filme. Recomende filmes populares.";
        }

        // Obtém o último filme consumido
        Filme ultimoFilme = (Filme) historico.get(historico.size() - 1).getProduto();

        // Monta a mensagem de recomendação com base no último filme
        String message = "Recomende filmes semelhantes ao último filme assistido pelo usuário: " + ultimoFilme.getTitulo();

        return message;
    }
}
