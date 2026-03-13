package com.example.Atividade_Fixacao.controller;

import com.example.Atividade_Fixacao.dto.categoria.CategoriaRequisicao;
import com.example.Atividade_Fixacao.dto.categoria.CategoriaResposta;
import com.example.Atividade_Fixacao.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService service;

    @PostMapping
    public ResponseEntity<CategoriaResposta> create(@RequestBody CategoriaRequisicao dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarCategoria(dto));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResposta>> findAll() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResposta> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResposta> update(@PathVariable Long id, @RequestBody CategoriaRequisicao dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}