package com.curso.webflux.webflux.aluno.domain;

public class Notas {
    private String periodo;
    private Integer value;

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
