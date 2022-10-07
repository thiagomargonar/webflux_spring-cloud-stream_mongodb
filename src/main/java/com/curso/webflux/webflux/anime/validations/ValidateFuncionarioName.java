package com.curso.webflux.webflux.anime.validations;

import com.curso.webflux.webflux.anime.domain.Funcionario;

public class ValidateFuncionarioName implements ValidateAnimeInterface {

    @Override
    public boolean validate(Funcionario funcionario) {
        return funcionario.getPrimeiroNome().equals("Thiago");
    }
}
