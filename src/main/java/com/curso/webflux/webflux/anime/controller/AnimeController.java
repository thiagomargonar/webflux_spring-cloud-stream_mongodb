package com.curso.webflux.webflux.anime.controller;


import com.curso.webflux.webflux.anime.domain.PrimeirosFuncionarios;
import com.curso.webflux.webflux.anime.service.AnimeService;
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
    public Flux<PrimeirosFuncionarios> listAll() {
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
    public Mono<PrimeirosFuncionarios> listById(@PathVariable int id) {
        return animeService.findById(id);
    }

    @PostMapping
    public Mono<PrimeirosFuncionarios> save(@RequestBody Mono<PrimeirosFuncionarios> anime){
        return anime.flatMap(animeService::save);
    }

    @PutMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> update(@PathVariable int id,@RequestBody PrimeirosFuncionarios anime){
        return animeService.update(id, anime);
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable BigInteger id){
        return animeService.delete(id);
    }
}
