package com.example.Atividade_Fixacao.controller;

import com.example.Atividade_Fixacao.dto.setor.SetorRequisicao;
import com.example.Atividade_Fixacao.dto.setor.SetorResposta;
import com.example.Atividade_Fixacao.service.SetorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/setores")
@RequiredArgsConstructor
public class SetorController {

    private final SetorService service;

    @PostMapping
    public ResponseEntity<SetorResposta> create(@RequestBody SetorRequisicao dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarSetor(dto));
    }

    @GetMapping
    public ResponseEntity<List<SetorResposta>> findAll() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SetorResposta> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SetorResposta> update(@PathVariable Long id, @RequestBody SetorRequisicao dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}