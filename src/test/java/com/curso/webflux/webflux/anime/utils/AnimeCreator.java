package com.curso.webflux.webflux.anime.utils;

import com.curso.webflux.webflux.anime.domain.PrimeirosFuncionarios;

public class AnimeCreator {
    public static PrimeirosFuncionarios creatorAnimeToBeSaved(){
        return PrimeirosFuncionarios.builder()
                .id(5)
                .anime("ANIME_1").build();
    }

    public static PrimeirosFuncionarios creatorAnimeToInvalidId(){
        return PrimeirosFuncionarios.builder()
                .id(1)
                .anime("ANIME_1").build();
    }

    public static PrimeirosFuncionarios creatorAnimeToInvalidName(){
        return PrimeirosFuncionarios.builder()
                .id(1)
                .anime("Thiago").build();
    }
}
