package com.curso.webflux.webflux.anime.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Table("ANIME")
public class Anime {

    @Id
    private Integer id;

    private String anime;
}
