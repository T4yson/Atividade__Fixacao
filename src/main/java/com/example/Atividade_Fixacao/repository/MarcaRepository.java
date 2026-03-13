package com.example.Atividade_Fixacao.repository;

import com.example.Atividade_Fixacao.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {
}
