package com.curso.webflux.webflux.anime.validations;

import com.curso.webflux.webflux.anime.domain.PrimeirosFuncionarios;
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

    public Mono validationsAnime(PrimeirosFuncionarios anime){
        if(containsErros(anime)){
            return Mono.error(new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, AnimeExceptions.ANIME_INVALIDO.getTexto()));
        }
        return Mono.just(anime);
    }

    private boolean containsErros(PrimeirosFuncionarios anime) {
        return validateAnimeInterfaces.stream().anyMatch(validateAnimeInterface -> validateAnimeInterface.validate(anime));
    }
}
