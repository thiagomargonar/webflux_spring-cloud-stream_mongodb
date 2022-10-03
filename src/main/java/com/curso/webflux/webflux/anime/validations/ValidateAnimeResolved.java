package com.curso.webflux.webflux.anime.validations;

import com.curso.webflux.webflux.anime.domain.Anime;
import com.curso.webflux.webflux.anime.exceptions.AnimeExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class ValidateAnimeResolved {

    List<ValidateAnimeInterface> validateAnimeInterfaces;

    public ValidateAnimeResolved() {
        validateAnimeInterfaces = new ArrayList<>();
        validateAnimeInterfaces.add(new ValidateAnimeID());
        validateAnimeInterfaces.add(new ValidateAnimeTitulo());
    }

    public Mono validationsAnime(Anime anime){
        boolean valid = false;
        for(ValidateAnimeInterface validando:validateAnimeInterfaces){
            valid = validando.validate(anime, valid);
            if(valid){
                break;
            }
        }

        if(valid){
            return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, AnimeExceptions.ANIME_INVALIDO.getTexto()));
        }
        return Mono.just(anime);
    }
}
