package com.curso.webflux.webflux.funcionario.validations;

import com.curso.webflux.webflux.funcionario.domain.Funcionario;

import java.math.BigInteger;

public class ValidateFuncionarioID implements ValidateAnimeInterface {

    @Override
    public boolean validate(Funcionario funcionario) {
        return funcionario.getId().equals(new BigInteger("1"));
    }
}
