package com.curso.webflux.webflux.anime.repository;

import com.curso.webflux.webflux.anime.domain.PrimeirosFuncionarios;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface AnimeRepository extends ReactiveMongoRepository<PrimeirosFuncionarios, BigInteger> {
    @Query(value = "{'Primeiro_Nome': ?0}")
    Mono<PrimeirosFuncionarios> findByName(String nome);

    //db.funcionario.find( { salario: { $gte: 150 } } )
    @Query(value = "{'salario': { $gte: ?0}, 'Primeiro_Nome': ?1}")
    Flux<PrimeirosFuncionarios> findByNoSalario(double valor, String nome);
}
