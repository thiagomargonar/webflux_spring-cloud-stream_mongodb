package com.curso.webflux.webflux.funcionario.validations;

import com.curso.webflux.webflux.funcionario.domain.Funcionario;

public class ValidateFuncionarioName implements ValidateAnimeInterface {

    @Override
    public boolean validate(Funcionario funcionario) {
        return funcionario.getPrimeiroNome().equals("Thiago");
    }
}
