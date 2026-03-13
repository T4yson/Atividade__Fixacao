package com.example.Atividade_Fixacao.service;

import com.example.Atividade_Fixacao.dto.cliente.ClienteRequisicao;
import com.example.Atividade_Fixacao.dto.cliente.ClienteResposta;
import com.example.Atividade_Fixacao.mapper.ClienteMapper;
import com.example.Atividade_Fixacao.model.Cliente;
import com.example.Atividade_Fixacao.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;

    @Transactional
    public ClienteResposta criarProduto(ClienteRequisicao dto) {
        Cliente entidade = mapper.paraEntidade(dto);
        return mapper.paraResposta(repository.save(entidade));
    }

    @Transactional(readOnly = true)
    public List<ClienteResposta> buscarTodos() {
        return repository.findAll().stream()
                .map(mapper::paraResposta)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClienteResposta buscarPorId(Long id) {
        Cliente entidade = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return mapper.paraResposta(entidade);
    }

    @Transactional
    public ClienteResposta atualizar(Long id, ClienteRequisicao dto) {
        Cliente entidade = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        entidade.setNome(dto.nome());
        entidade.setEmail(dto.email());

        return mapper.paraResposta(repository.save(entidade));
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado");
        }
        repository.deleteById(id);
    }
}