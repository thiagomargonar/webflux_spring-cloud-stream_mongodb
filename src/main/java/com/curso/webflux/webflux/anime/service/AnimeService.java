package com.curso.webflux.webflux.anime.service;


import com.curso.webflux.webflux.anime.domain.Anime;
import com.curso.webflux.webflux.anime.exceptions.AnimeExceptions;
import com.curso.webflux.webflux.anime.repository.AnimeRepository;
import com.curso.webflux.webflux.anime.validations.ValidateAnimeNotFound;
import com.curso.webflux.webflux.anime.validations.ValidateAnimeResolved;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;

    public Flux<Anime> findAll() {
        return animeRepository.findAll();
    }

    public Mono<Anime> findById(int id) {
        return animeRepository.findById(id)
                .switchIfEmpty(Mono.error(ValidateAnimeNotFound.getAnime_not_found()))
                .flatMap(nime -> new ValidateAnimeResolved().validationsAnime(nime));
    }

    public Mono<Anime> save(Anime anime) {
        return animeRepository.save(anime);
    }

    public Mono<Void> update(int id, Anime anime) {
        return findById(id)
                .flatMap(anime1 -> animeRepository.save(anime))
                .then();
    }

    public Mono<Void> delete(int id) {
        return findById(id)
                .flatMap(animeRepository::delete);
    }
}
