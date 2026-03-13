package com.example.Atividade_Fixacao.service;

import com.example.Atividade_Fixacao.dto.setor.SetorRequisicao;
import com.example.Atividade_Fixacao.dto.setor.SetorResposta;
import com.example.Atividade_Fixacao.mapper.SetorMapper;
import com.example.Atividade_Fixacao.model.Setor;
import com.example.Atividade_Fixacao.repository.SetorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SetorService {

    private final SetorRepository repository;
    private final SetorMapper mapper;

    @Transactional
    public SetorResposta criarProduto(SetorRequisicao dto) {
        Setor entidade = mapper.paraEntidade(dto);
        return mapper.paraResposta(repository.save(entidade));
    }

    @Transactional(readOnly = true)
    public List<SetorResposta> buscarTodos() {
        return repository.findAll().stream()
                .map(mapper::paraResposta)
                .toList();
    }

    @Transactional(readOnly = true)
    public SetorResposta buscarPorId(Long id) {
        Setor entidade = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Setor não encontrado"));
        return mapper.paraResposta(entidade);
    }

    @Transactional
    public SetorResposta atualizar(Long id, SetorRequisicao dto) {
        Setor entidade = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Setor não encontrado"));

        entidade.setNome(dto.nome());
        entidade.setSigla(dto.sigla());

        return mapper.paraResposta(repository.save(entidade));
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Setor não encontrado");
        }
        repository.deleteById(id);
    }
}