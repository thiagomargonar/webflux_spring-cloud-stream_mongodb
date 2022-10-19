package com.curso.webflux.webflux.aluno.service;

import com.curso.webflux.webflux.aluno.domain.Aluno;
import com.curso.webflux.webflux.aluno.repository.AlunoRepository;
import org.bson.Document;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;

@Service
public class AlunoService {
    private final AlunoRepository alunoRepository;
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final StreamBridge streamBridge;

    private TesteProducerBean testeProducerBean;

    public AlunoService(AlunoRepository alunoRepository, ReactiveMongoTemplate reactiveMongoTemplate,
                        StreamBridge streamBridge, TesteProducerBean testeProducerBean) {
        this.alunoRepository = alunoRepository;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.streamBridge = streamBridge;
        this.testeProducerBean = testeProducerBean;
    }


    public Mono<Aluno> saveAluno(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public Flux<Aluno> findALl() {
        var aluno = alunoRepository.findAll();
        return aluno
                .doOnNext(aluno1 -> {
                    if (aluno1.getNome().equals("margonar")) {
                        //TODO Message Builder - set headers payload...
                        streamBridge.send("producer_aluno", aluno1);
                        streamBridge.send("producer_aluno_log", "logou aluno");
                    }
                })
                .doOnError(throwable -> {
                    System.out.println("poderia enviar uma mensagem para outro topic passando throwable.printStackTrace() personalizado");
                });
    }


    @Transactional
    public Mono<Void> updateCurso(String id, String curso) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update()
                .set("curso.nome", curso);

        return reactiveMongoTemplate.findAndModify(query, update, Aluno.class)
                .delaySubscription(Duration.ofMillis(15000))
                .doOnSuccess(aluno -> System.out.println("terminou update -  iniciando envio de mensagem"))
                .doOnNext(aluno -> testeProducerBean.supplier())
                .then();
    }

    @Transactional
    public void updateCurso1(String id, String curso) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update()
                .set("curso.nome", curso);

        reactiveMongoTemplate.findAndModify(query, update, Aluno.class).block();
    }

    @Transactional
    public void updateCurso2(String id, String curso) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update()
                .set("curso.nome", curso);

        reactiveMongoTemplate.findAndModify(query, update, Aluno.class)
                .delaySubscription(Duration.ofMillis(15000))
                .doOnSuccess(aluno -> System.out.println("terminou update"))
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

    public Mono<List<Document>> lookupOperation() {


        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("aluno")
                .localField("item")
                .foreignField("curso.nome")
                .as("alunosMatriculados");

        ProjectionOperation operation = new ProjectionOperation()
                .andExclude("_id");

        GroupOperation groupOperation = group("item", "price", "alunosMatriculados");

        Aggregation aggregation = Aggregation.newAggregation(
                lookupOperation,
                operation,
                groupOperation);

        return reactiveMongoTemplate.aggregate(aggregation, "modulos", Document.class).collectList();
    }


    @Bean
    public Consumer<Aluno> consumer() {
        return message -> {
            System.out.println("received " + message.toString() + " " + message.getNome());
        };
    }

    @Bean
    @Scheduled(fixedDelay = 1000)
    public Consumer<String> consumerlog() {
        return stringStringKStream -> {
            System.out.println("received_Log " + stringStringKStream);
        };
    }
}













