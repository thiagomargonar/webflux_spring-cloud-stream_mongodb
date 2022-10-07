package com.curso.webflux.webflux.anime.validations;

import com.curso.webflux.webflux.anime.domain.Funcionario;

import java.math.BigInteger;

public class ValidateFuncionarioID implements ValidateAnimeInterface {

    @Override
    public boolean validate(Funcionario funcionario) {
        return funcionario.getId().equals(new BigInteger("1"));
    }
}
