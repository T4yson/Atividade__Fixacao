package com.example.Atividade_Fixacao.dto.produtor;

import java.math.BigDecimal;

public record ProdutoResposta(

        Long id,
        String nome,
        BigDecimal preco
) {
}
