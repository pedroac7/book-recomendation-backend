package com.imd.br.bookRecomendation.Service;

import com.imd.br.bookRecomendation.Model.Historico;
import com.imd.br.bookRecomendation.Model.Livro;
import com.imd.br.bookRecomendation.Model.Usuario;
import com.imd.br.bookRecomendation.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecomendationService {

    @Autowired
    private UsuarioRepository ur;

    @Autowired
    private HistoricoService hs;

    public String getGeneroById(Long id){
        Optional<Usuario> usuario = ur.findById(id);

        String genero = usuario.get().getGeneroPreferido();

        if (genero.isEmpty()){
            genero = "Drama";
        }

        return genero;
    }

    public Livro getUltimoLivro(Long id){

        List<Historico> historico = hs.buscarPorUsuario(id);

        int tam = historico.size();

        return historico.get(tam-1).getLivro();
    }

}
