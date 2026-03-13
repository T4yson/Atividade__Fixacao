package com.example.Atividade_Fixacao.mapper;

import com.example.Atividade_Fixacao.dto.categoria.CategoriaRequisicao;
import com.example.Atividade_Fixacao.dto.categoria.CategoriaResposta;
import com.example.Atividade_Fixacao.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public Categoria paraEntidade(CategoriaRequisicao dto) {
        return new Categoria(null, dto.nome(), dto.email());

    }

    public CategoriaResposta paraResposta(Categoria e) {
        return new CategoriaResposta(e.getId(), e.getNome(), e.getDescricao());
    }
}
