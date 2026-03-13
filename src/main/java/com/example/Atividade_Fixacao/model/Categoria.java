package com.example.Atividade_Fixacao.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.IdGeneratorType;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Categoria {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String nome;
    private String descricao;
}
