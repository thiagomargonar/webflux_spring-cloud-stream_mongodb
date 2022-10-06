package com.curso.webflux.webflux.anime.validations;

import com.curso.webflux.webflux.anime.domain.PrimeirosFuncionarios;

public class ValidateAnimeTitulo implements ValidateAnimeInterface {

    @Override
    public boolean validate(PrimeirosFuncionarios anime) {
        return anime.getPrimeiro_Nome().equals("Thiago");
    }
}
