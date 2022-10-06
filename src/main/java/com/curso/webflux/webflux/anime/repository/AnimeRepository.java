package com.curso.webflux.webflux.anime.repository;

import com.curso.webflux.webflux.anime.domain.PrimeirosFuncionarios;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AnimeRepository extends ReactiveMongoRepository<PrimeirosFuncionarios,Integer> {

}
