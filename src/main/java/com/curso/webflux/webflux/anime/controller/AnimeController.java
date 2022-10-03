package com.curso.webflux.webflux.anime.controller;


import com.curso.webflux.webflux.anime.domain.Anime;
import com.curso.webflux.webflux.anime.service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RequiredArgsConstructor
@RestController
@RequestMapping("/anime")
@Slf4j
public class AnimeController {

    private final AnimeService animeService;

    @GetMapping()
    public Flux<Anime> listAll() {
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
    public Mono<Anime> listById(@PathVariable int id) {
        return animeService.findById(id);
    }

    @PostMapping
    public Mono<Anime> save(@RequestBody Mono<Anime> anime){
        return anime.doOnNext(anime1 -> System.out.println(anime1))
                .flatMap(animeService::save);
                //.doOnError(//captura em todos doONError da pipe);
    }

    @PutMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> update(@PathVariable int id,@RequestBody Anime anime){
        return animeService.update(id, anime);
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable int id){
        return animeService.delete(id);
    }
}
