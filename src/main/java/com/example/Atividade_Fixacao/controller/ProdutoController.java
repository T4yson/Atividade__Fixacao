package com.example.Atividade_Fixacao.controller;

import com.example.Atividade_Fixacao.dto.produto.ProdutoRequisicao;
import com.example.Atividade_Fixacao.dto.produto.ProdutoResposta;
import com.example.Atividade_Fixacao.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService service;

    @PostMapping
    public ResponseEntity<ProdutoResposta> create(@RequestBody ProdutoRequisicao dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarProduto(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResposta>> findAll() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResposta> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResposta> update(@PathVariable Long id, @RequestBody ProdutoRequisicao dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}