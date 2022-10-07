package com.curso.webflux.webflux.anime.service;


import com.curso.webflux.webflux.anime.domain.Funcionario;
import com.curso.webflux.webflux.anime.repository.AnimeRepository;
import com.curso.webflux.webflux.anime.validations.ValidateAnimeNotFound;
import com.curso.webflux.webflux.anime.validations.ValidateFuncionarioResolved;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public Flux<Funcionario> findAll() {
        return animeRepository.findAll();
    }

    public Mono<Funcionario> findById(String id) {
        return animeRepository.findById(id)
                .switchIfEmpty(Mono.error(ValidateAnimeNotFound.getFuncionarioNotFound()))
                .flatMap(nime -> new ValidateFuncionarioResolved().validationsFuncionario(nime));
    }

    @Transactional(rollbackFor = Exception.class)
    public Mono<Funcionario> save(Funcionario anime) {
        return animeRepository.insert(anime);
    }

    public Mono<Void> update(BigInteger id, Funcionario funcionario) {

        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().set("",funcionario)
                .set("Sexo","F");

        return reactiveMongoTemplate.findAndModify(query,update, Funcionario.class).then();

//        return findById(id)
//                .flatMap(anime1 -> animeRepository.save(funcionario))
//                .then();
    }

    public Mono<Void> delete(String id) {
        return findById(id)
                .flatMap(animeRepository::delete);
    }

    public Mono<Funcionario> findByName(String nome) {
        return animeRepository.findByName(nome);
    }

    public Flux<Funcionario> findNoSalario(double valor, String nome) {
        return animeRepository.findByNoSalario(valor, nome);
    }
}
