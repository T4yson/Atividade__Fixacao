package com.example.Atividade_Fixacao.mapper;

import com.example.Atividade_Fixacao.dto.cliente.ClienteRequisicao;
import com.example.Atividade_Fixacao.dto.cliente.ClienteResposta;
import com.example.Atividade_Fixacao.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente paraEntidade(ClienteRequisicao dto) {
        return new Cliente(null, dto.nome(), dto.email());

    }

    public ClienteResposta paraResposta(Cliente e) {
        return new ClienteResposta(e.getId(), e.getNome(), e.getEmail());
    }
}
