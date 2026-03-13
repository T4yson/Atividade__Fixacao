package com.example.Atividade_Fixacao.service;

import com.example.Atividade_Fixacao.dto.marca.MarcaRequisicao;
import com.example.Atividade_Fixacao.dto.marca.MarcaResposta;
import com.example.Atividade_Fixacao.mapper.MarcaMapper;
import com.example.Atividade_Fixacao.model.Marca;
import com.example.Atividade_Fixacao.repository.MarcaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarcaService {

    private final MarcaRepository repository;
    private final MarcaMapper mapper;

    @Transactional
    public MarcaResposta criarProduto(MarcaRequisicao dto) {
        Marca entidade = mapper.paraEntidade(dto);
        return mapper.paraResposta(repository.save(entidade));
    }

    @Transactional(readOnly = true)
    public List<MarcaResposta> buscarTodos() {
        return repository.findAll().stream()
                .map(mapper::paraResposta)
                .toList();
    }

    @Transactional(readOnly = true)
    public MarcaResposta buscarPorId(Long id) {
        Marca entidade = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca não encontrada"));
        return mapper.paraResposta(entidade);
    }

    @Transactional
    public MarcaResposta atualizar(Long id, MarcaRequisicao dto) {
        Marca entidade = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca não encontrada"));

        entidade.setNome(dto.nome());
        entidade.setPaisOrigem(dto.paisOrigem());

        return mapper.paraResposta(repository.save(entidade));
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Marca não encontrada");
        }
        repository.deleteById(id);
    }
}