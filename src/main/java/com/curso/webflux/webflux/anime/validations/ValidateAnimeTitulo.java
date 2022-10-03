package com.curso.webflux.webflux.anime.validations;

import com.curso.webflux.webflux.anime.domain.Anime;

public class ValidateAnimeTitulo implements ValidateAnimeInterface {

    @Override
    public boolean validate(Anime anime, boolean validado) {
        if (anime.getAnime().equals("Thiago")) {
            return true;
        }
        return validado;
    }
}
