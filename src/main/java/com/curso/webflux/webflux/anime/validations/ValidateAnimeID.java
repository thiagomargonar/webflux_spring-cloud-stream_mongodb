package com.curso.webflux.webflux.anime.validations;

import com.curso.webflux.webflux.anime.domain.PrimeirosFuncionarios;

import java.math.BigInteger;

public class ValidateAnimeID implements ValidateAnimeInterface {

    @Override
    public boolean validate(PrimeirosFuncionarios anime) {
        return anime.getId().equals(new BigInteger("1"));
    }
}
