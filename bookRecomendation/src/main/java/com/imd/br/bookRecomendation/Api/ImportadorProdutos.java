package com.imd.br.bookRecomendation.Api;

import com.imd.br.bookRecomendation.Service.FilmeService;
import com.imd.br.bookRecomendation.Service.TMDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImportadorProdutos {

    @Autowired
    private TMDbService tmdbService;
    @Autowired
    private FilmeService filmeService;

    public void importarProdutos(String query) {
        int page = 1;
        boolean hasMoreData = true;

        while (hasMoreData) {
            String response = tmdbService.buscarFilmes(query, page);

            try {
                filmeService.salvarProdutos(response);
                page++;
            } catch (Exception e) {
                e.printStackTrace();
                hasMoreData = false; // Para em caso de erro
            }
        }
    }

}
