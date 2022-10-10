package com.curso.webflux.webflux.aluno.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "curso")
public class Curso {

    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
