package com.curso.webflux.webflux.aluno.service;

import com.curso.webflux.webflux.aluno.domain.Aluno;
import com.curso.webflux.webflux.aluno.repository.AlunoRepository;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.replaceRoot;

@Service
public class AlunoService {
    private final AlunoRepository alunoRepository;
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public AlunoService(AlunoRepository alunoRepository, ReactiveMongoTemplate reactiveMongoTemplate) {
        this.alunoRepository = alunoRepository;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }


    public Mono<Aluno> saveAluno(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public Flux<Aluno> findALl() {
        return alunoRepository.findAll();
    }

    @Transactional
    public void updateAluno(String id, String curso) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update()
                .set("curso.nome", curso);

        reactiveMongoTemplate.findAndModify(query, update, Aluno.class)
                .subscribe();
    }

    public Flux<Aluno> findByCurso(String curso) {

        return alunoRepository.findByCurso(curso, "Thiago");
    }

    public Mono<List<Aluno>> findAlunosAprovados() {
        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();
        criteriaList.add(Criteria.where("valor").gte(6));
        criteriaList.forEach(query::addCriteria);
        return reactiveMongoTemplate.find(query, Aluno.class).collectList();
    }

    public Mono<List<Aluno>> getTheBestAluno() {
        var query = new Query().with(Sort.by(Sort.Direction.DESC, "nota.value")).limit(1);
        return reactiveMongoTemplate.find(query, Aluno.class).collectList();
    }


    public Mono<List<Document>> getTheBestAluno2() {

        UnwindOperation notaValue = Aggregation.unwind("nota");
        GroupOperation groupOperation1 = group("nome", "habilidades")
                .avg("nota.value").as("media")
                .count().as("notasLancadas")
                .sum("nota.value").as("totalNotas");

        Aggregation aggregation = Aggregation.newAggregation(
                notaValue,
                groupOperation1);

        return reactiveMongoTemplate.aggregate(aggregation, "aluno", Document.class).collectList();
    }

    public Mono<List<Document>> lookupOperation(){


        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("aluno")
                .localField("item")
                .foreignField("curso.nome")
                .as("alunosMatriculados");

        ProjectionOperation operation = new ProjectionOperation()
                .andExclude("_id");

        GroupOperation groupOperation = group("item","price","alunosMatriculados");

        Aggregation aggregation = Aggregation.newAggregation(
                lookupOperation,
                operation,
                groupOperation);

        return reactiveMongoTemplate.aggregate(aggregation, "modulos", Document.class).collectList();
    }


}

