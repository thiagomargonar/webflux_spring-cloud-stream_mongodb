package com.curso.webflux.webflux.anime.service;

import com.curso.webflux.webflux.anime.domain.Anime;
import com.curso.webflux.webflux.anime.repository.AnimeRepository;
import com.curso.webflux.webflux.anime.utils.AnimeCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class AnimeServiceTest {


    @InjectMocks
    private AnimeService animeService;

    @Mock
    private AnimeRepository animeRepository;

    private Anime anime = AnimeCreator.creatorAnimeToBeSaved();

}