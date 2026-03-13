package com.example.Atividade_Fixacao.mapper;

import com.example.Atividade_Fixacao.dto.setor.SetorRequisicao;
import com.example.Atividade_Fixacao.dto.setor.SetorResposta;
import com.example.Atividade_Fixacao.model.Setor;
import org.springframework.stereotype.Component;

@Component
public class SetorMapper {

    public Setor paraEntidade(SetorRequisicao dto) {
        return new Setor(null, dto.sigla(), dto.nome());

    }

    public SetorResposta paraResposta(Setor e) {
        return new SetorResposta(e.getId(), e.getSigla(), e.getNome());
    }
}
