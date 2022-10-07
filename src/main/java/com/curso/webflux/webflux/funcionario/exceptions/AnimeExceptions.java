package com.curso.webflux.webflux.funcionario.exceptions;

public enum AnimeExceptions {
    ANIME_NAO_ECONTRADO("Anime not found"),
    ANIME_INVALIDO("Invalidate Anime");

    private final String texto;

    AnimeExceptions(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}
