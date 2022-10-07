package com.curso.webflux.webflux.funcionario.validations;

import com.curso.webflux.webflux.funcionario.domain.Funcionario;
import com.curso.webflux.webflux.funcionario.exceptions.AnimeExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class ValidateFuncionarioResolved {

    List<ValidateAnimeInterface> validateAnimeInterfaces;

    public ValidateFuncionarioResolved() {
        validateAnimeInterfaces = new ArrayList<>();
        validateAnimeInterfaces.add(new ValidateFuncionarioID());
        validateAnimeInterfaces.add(new ValidateFuncionarioName());
    }

    public Mono validationsFuncionario(Funcionario funcionario){
        if(containsErros(funcionario)){
            return Mono.error(new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, AnimeExceptions.ANIME_INVALIDO.getTexto()));
        }
        return Mono.just(funcionario);
    }

    private boolean containsErros(Funcionario funcionario) {
        return validateAnimeInterfaces.stream().anyMatch(validateAnimeInterface -> validateAnimeInterface.validate(funcionario));
    }
}
