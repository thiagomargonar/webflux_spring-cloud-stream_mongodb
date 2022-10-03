package com.curso.webflux.webflux.anime.controller;


import com.curso.webflux.webflux.anime.domain.Anime;
import com.curso.webflux.webflux.anime.service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/anime")
@Slf4j
public class AnimeController {

    private final AnimeService animeService;

    @GetMapping()
    public Flux<Anime> listAll() {
        return animeService.findAll();
    }


    @GetMapping(path = "{id}")
    public Mono<Anime> listById(@PathVariable int id) {
        return animeService.findById(id);
    }

    @PostMapping
    public Mono<Anime> save(@RequestBody Anime anime){
        return animeService.save(anime);
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
