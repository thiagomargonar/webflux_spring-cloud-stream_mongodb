package com.curso.webflux.webflux.funcionario.controller;


import com.curso.webflux.webflux.funcionario.domain.Funcionario;
import com.curso.webflux.webflux.funcionario.service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.time.Duration;

@RequiredArgsConstructor
@RestController
@RequestMapping("/anime")
@Slf4j
public class AnimeController {

    private final AnimeService animeService;

    @GetMapping()
    public Flux<Funcionario> listAll() {
        return animeService.findAll()
                .log()
                .doOnSubscribe(subscription ->  log.info(subscription.toString()))
                .doOnComplete(() -> getTest().subscribe());
    }

    private Mono<String> getTest() {
        return Mono.just("teste Marcos")
                .delayElement(Duration.ofMillis(4000))
                .doOnNext(s -> System.out.println("passou aqui: "+s));
    }


    @GetMapping(path = "{id}")
    public Mono<Funcionario> listById(@PathVariable String id) {
        return animeService.findById(id);
    }

    @GetMapping(path = "/nome/{nome}")
    public Mono<Funcionario> findByName(@PathVariable String nome) {
        return animeService.findByName(nome);
    }

    @GetMapping(path = "/salariodfnull/{valor}/{nome}")
    public Flux<Funcionario> findSemSalario(@PathVariable double valor, @PathVariable String nome) {
        return animeService.findNoSalario(valor,nome);
    }

    @PostMapping
    public Mono<Funcionario> save(@RequestBody Mono<Funcionario> anime){
        return anime.flatMap(animeService::save);
    }

    @PutMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> update(@PathVariable BigInteger id,@RequestBody Funcionario anime){
        return animeService.update(id, anime);
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable String id){
        return animeService.delete(id);
    }
}
