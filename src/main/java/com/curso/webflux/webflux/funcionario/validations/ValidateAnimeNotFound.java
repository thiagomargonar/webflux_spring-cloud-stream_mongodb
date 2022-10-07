package com.curso.webflux.webflux.funcionario.validations;

import com.curso.webflux.webflux.funcionario.exceptions.AnimeExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ValidateAnimeNotFound {
    public static ResponseStatusException getFuncionarioNotFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, AnimeExceptions.ANIME_NAO_ECONTRADO.getTexto());
    }
}
