package com.example.Atividade_Fixacao.service;

import com.example.Atividade_Fixacao.dto.categoria.CategoriaRequisicao;
import com.example.Atividade_Fixacao.dto.categoria.CategoriaResposta;
import com.example.Atividade_Fixacao.mapper.CategoriaMapper;
import com.example.Atividade_Fixacao.model.Categoria;
import com.example.Atividade_Fixacao.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repository;
    private final CategoriaMapper mapper;

    @Transactional
    public CategoriaResposta criarCategoria(CategoriaRequisicao dto) {
        Categoria entidade = mapper.paraEntidade(dto);
        return mapper.paraResposta(repository.save(entidade));
    }

    @Transactional(readOnly = true)
    public List<CategoriaResposta> buscarTodos() {
        return repository.findAll().stream()
                .map(mapper::paraResposta)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoriaResposta buscarPorId(Long id) {
        Categoria entidade = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        return mapper.paraResposta(entidade);
    }

    @Transactional
    public CategoriaResposta atualizar(Long id, CategoriaRequisicao dto) {
        Categoria entidade = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        entidade.setNome(dto.nome());
        entidade.setDescricao(dto.descricao());

        return mapper.paraResposta(repository.save(entidade));
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Categoria não encontrada");
        }
        repository.deleteById(id);
    }
}