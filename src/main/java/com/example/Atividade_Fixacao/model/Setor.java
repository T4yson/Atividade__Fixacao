package com.example.Atividade_Fixacao.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Setor {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String sigla;
    private String nome;
}