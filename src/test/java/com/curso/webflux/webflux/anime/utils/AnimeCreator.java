package com.curso.webflux.webflux.anime.utils;

import com.curso.webflux.webflux.anime.domain.Anime;

public class AnimeCreator {
    public static Anime creatorAnimeToBeSaved(){
        return Anime.builder()
                .id(5)
                .anime("ANIME_1").build();
    }

    public static Anime creatorAnimeToInvalidId(){
        return Anime.builder()
                .id(1)
                .anime("ANIME_1").build();
    }

    public static Anime creatorAnimeToInvalidName(){
        return Anime.builder()
                .id(1)
                .anime("Thiago").build();
    }
}
