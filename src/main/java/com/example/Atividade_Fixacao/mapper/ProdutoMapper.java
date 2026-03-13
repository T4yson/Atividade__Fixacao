package com.example.Atividade_Fixacao.mapper;

import com.example.Atividade_Fixacao.dto.produto.ProdutoRequisicao;
import com.example.Atividade_Fixacao.dto.produto.ProdutoResposta;
import com.example.Atividade_Fixacao.model.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    public Produto paraEntidade(ProdutoRequisicao dto) {
        return new Produto(null, dto.nome(), dto.preco());

    }

    public ProdutoResposta paraResposta(Produto e) {
        return new ProdutoResposta(e.getId(), e.getNome(), e.getPreco());
    }
}
