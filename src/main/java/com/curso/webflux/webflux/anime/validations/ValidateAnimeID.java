package com.curso.webflux.webflux.anime.validations;

import com.curso.webflux.webflux.anime.domain.Anime;

public class ValidateAnimeID implements ValidateAnimeInterface {

    @Override
    public boolean validate(Anime anime) {
        return anime.getId() == 1;
    }
}
