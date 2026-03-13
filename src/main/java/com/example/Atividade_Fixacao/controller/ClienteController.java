package com.example.Atividade_Fixacao.controller;

import com.example.Atividade_Fixacao.dto.cliente.ClienteRequisicao;
import com.example.Atividade_Fixacao.dto.cliente.ClienteResposta;
import com.example.Atividade_Fixacao.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @PostMapping
    public ResponseEntity<ClienteResposta> create(@RequestBody ClienteRequisicao dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarCliente(dto));
    }

    @GetMapping
    public ResponseEntity<List<ClienteResposta>> findAll() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResposta> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResposta> update(@PathVariable Long id, @RequestBody ClienteRequisicao dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}