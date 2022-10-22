package com.curso.webflux.webflux.aluno.service;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class TesteProducerBean {

    @Bean
    public Supplier<String> supplier() {
        return () -> {
            System.out.println("Sending Hello World suplier");
            return "Hello World - suplier";
        };
    }

    @Bean
    public Function<String, String> uppercase() {
        return value -> {
            System.out.println("received **> "+value);
            return value.toUpperCase();
        };
    }
}
