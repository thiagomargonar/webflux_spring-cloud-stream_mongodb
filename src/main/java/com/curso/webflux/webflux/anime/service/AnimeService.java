package com.curso.webflux.webflux.anime.service;


import com.curso.webflux.webflux.anime.domain.PrimeirosFuncionarios;
import com.curso.webflux.webflux.anime.repository.AnimeRepository;
import com.curso.webflux.webflux.anime.validations.ValidateAnimeNotFound;
import com.curso.webflux.webflux.anime.validations.ValidateAnimeResolved;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;

    public Flux<PrimeirosFuncionarios> findAll() {
        return animeRepository.findAll();
    }

    public Mono<PrimeirosFuncionarios> findById(int id) {
        return animeRepository.findById(id)
                .switchIfEmpty(Mono.error(ValidateAnimeNotFound.getAnime_not_found()))
                .flatMap(nime -> new ValidateAnimeResolved().validationsAnime(nime));
    }

    public Mono<PrimeirosFuncionarios> save(PrimeirosFuncionarios anime) {
        return animeRepository.save(anime);
    }

    public Mono<Void> update(int id, PrimeirosFuncionarios anime) {
        return findById(id)
                .flatMap(anime1 -> animeRepository.save(anime))
                .then();
    }

    public Mono<Void> delete(int id) {
        return findById(id)
                .flatMap(animeRepository::delete);
    }
}
