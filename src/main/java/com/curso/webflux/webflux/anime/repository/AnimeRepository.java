package com.curso.webflux.webflux.anime.repository;

import com.curso.webflux.webflux.anime.domain.Anime;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AnimeRepository extends ReactiveCrudRepository<Anime,Integer> {

}
