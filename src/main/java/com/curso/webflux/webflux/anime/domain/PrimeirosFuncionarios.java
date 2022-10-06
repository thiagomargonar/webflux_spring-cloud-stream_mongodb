package com.curso.webflux.webflux.anime.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Document("funcionario")
public class PrimeirosFuncionarios {

    @Id
    private BigInteger id;

    private String Primeiro_Nome;

    private String Sexo;

    private Double salario;
}
