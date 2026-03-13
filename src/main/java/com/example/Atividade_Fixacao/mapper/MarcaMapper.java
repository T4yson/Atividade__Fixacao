package com.example.Atividade_Fixacao.mapper;

import com.example.Atividade_Fixacao.dto.marca.MarcaRequisicao;
import com.example.Atividade_Fixacao.dto.marca.MarcaResposta;
import com.example.Atividade_Fixacao.model.Marca;
import org.springframework.stereotype.Component;

@Component
public class MarcaMapper {

    public Marca paraEntidade(MarcaRequisicao dto) {
        return new Marca(null, dto.nome(), dto.paisOrigem());

    }

    public MarcaResposta paraResposta(Marca e) {
        return new MarcaResposta(e.getId(), e.getNome(), e.getPaisOrigem());
    }
}
