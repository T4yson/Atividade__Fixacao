package com.example.Atividade_Fixacao.service;

import com.example.Atividade_Fixacao.dto.produto.ProdutoRequisicao;
import com.example.Atividade_Fixacao.dto.produto.ProdutoResposta;
import com.example.Atividade_Fixacao.mapper.ProdutoMapper;
import com.example.Atividade_Fixacao.model.Produto;
import com.example.Atividade_Fixacao.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;
    private final ProdutoMapper mapper;

    @Transactional
    public ProdutoResposta criarProduto(ProdutoRequisicao dto) {
        Produto entity = mapper.paraEntidade(dto);
        return mapper.paraResposta(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<ProdutoResposta> buscarTodos() {
        return repository.findAll().stream()
                .map(mapper::paraResposta)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProdutoResposta buscarPorId(Long id) {
        Produto entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return mapper.paraResposta(entity);
    }

    @Transactional
    public ProdutoResposta atualizar(Long id, ProdutoRequisicao dto) {
        Produto entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        entity.setNome(dto.nome());
        entity.setPreco(dto.preco());

        return mapper.paraResposta(repository.save(entity));
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado");
        }
        repository.deleteById(id);
    }
}