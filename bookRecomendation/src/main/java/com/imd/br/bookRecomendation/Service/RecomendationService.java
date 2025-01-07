package com.imd.br.bookRecomendation.Service;

public abstract class RecomendationService {

    public abstract String getMessageByGenero(Long id);

    public abstract String getMessageByUltimoLivro(Long id);
}
