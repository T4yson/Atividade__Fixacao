package com.example.Atividade_Fixacao.controller;

import com.example.Atividade_Fixacao.dto.marca.MarcaRequisicao;
import com.example.Atividade_Fixacao.dto.marca.MarcaResposta;
import com.example.Atividade_Fixacao.model.Marca;
import com.example.Atividade_Fixacao.service.MarcaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/marcas")
@RequiredArgsConstructor
public class MarcaController {

    private final MarcaService service;

    @PostMapping
    public ResponseEntity<MarcaResposta> create(@RequestBody MarcaRequisicao dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarMarca(dto));
    }

    @GetMapping
    public ResponseEntity<List<MarcaResposta>> findAll() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaResposta> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarcaResposta> update(@PathVariable Long id, @RequestBody MarcaRequisicao dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}