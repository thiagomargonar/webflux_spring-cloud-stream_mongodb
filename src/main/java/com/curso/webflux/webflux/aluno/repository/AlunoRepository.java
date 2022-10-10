package com.curso.webflux.webflux.aluno.repository;

import com.curso.webflux.webflux.aluno.domain.Aluno;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface AlunoRepository extends ReactiveMongoRepository<Aluno, String> {

    @Query("{$or : [{'curso.nome' : ?0}], nome : ?1 }")
    Flux<Aluno> findByCurso(String name, String name2);
}
