package com.curso.webflux.webflux.anime.validations;

import com.curso.webflux.webflux.anime.domain.Anime;
import reactor.core.publisher.Mono;

public interface ValidateAnimeInterface {
    public boolean validate(Anime anime, boolean validado);
}
