package com.curso.webflux.webflux.aluno.controller;

import com.curso.webflux.webflux.aluno.domain.Aluno;
import com.curso.webflux.webflux.aluno.service.AlunoService;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    public Mono<Aluno> save(@RequestBody Mono<Aluno> alunoMono) {
        return alunoMono.flatMap(alunoService::saveAluno);
    }

    @GetMapping
    public Flux<Aluno> findALl() {
        return alunoService.findALl();
    }

    @GetMapping(path = "/{curso}")
    public Flux<Aluno> findByCurso(@PathVariable String curso) {
        return alunoService.findByCurso(curso);
    }

    @GetMapping(path = "/aprovados")
    public Mono<List<Aluno>> findByCurso() {
        return alunoService.findAlunosAprovados();
    }

    @GetMapping(path = "/best-estutant")
    public Mono<List<Aluno>> getTheBestStundant() {
        return alunoService.getTheBestAluno();
    }

    @GetMapping(path = "/best-estutant2")
    public Mono<List<Document>> getTheBestStundant2() {
        return alunoService.getTheBestAluno2();
    }

    @GetMapping(path = "/lookup")
    public Mono<List<Document>> getlookup() {
        return alunoService.lookupOperation();
    }

    @PutMapping(path = "/{id}")
    public void update(@PathVariable String id, @RequestBody String curso) {
        alunoService.updateAluno(id, curso);
    }
}
