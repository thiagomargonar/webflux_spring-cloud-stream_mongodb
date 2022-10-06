package com.curso.webflux.webflux.anime.service;


import com.curso.webflux.webflux.anime.domain.PrimeirosFuncionarios;
import com.curso.webflux.webflux.anime.repository.AnimeRepository;
import com.curso.webflux.webflux.anime.validations.ValidateAnimeNotFound;
import com.curso.webflux.webflux.anime.validations.ValidateAnimeResolved;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.logging.Filter;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;

    public Flux<PrimeirosFuncionarios> findAll() {
        return animeRepository.findAll();
    }

    public Mono<PrimeirosFuncionarios> findById(BigInteger id) {
        return animeRepository.findById(id)
                .switchIfEmpty(Mono.error(ValidateAnimeNotFound.getAnime_not_found()))
                .flatMap(nime -> new ValidateAnimeResolved().validationsAnime(nime));
    }

    @Transactional(rollbackFor = Exception.class)
    public Mono<PrimeirosFuncionarios> save(PrimeirosFuncionarios anime) {
        return animeRepository.insert(anime);
    }

    public Mono<Void> update(BigInteger id, PrimeirosFuncionarios anime) {
        return findById(id)
                .flatMap(anime1 -> animeRepository.save(anime))
                .then();
    }

    public Mono<Void> delete(BigInteger id) {
        return findById(id)
                .flatMap(animeRepository::delete);
    }

    public Mono<PrimeirosFuncionarios> findByName(String nome) {
        return animeRepository.findByName(nome);
    }

    public Flux<PrimeirosFuncionarios> findNoSalario(double valor, String nome) {
        return animeRepository.findByNoSalario(valor, nome);
    }
}
