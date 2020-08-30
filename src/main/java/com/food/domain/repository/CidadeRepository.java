package com.food.domain.repository;

import com.food.domain.model.Cidade;

import javax.transaction.Transactional;
import java.util.List;

public interface CidadeRepository {
    List<Cidade> todas();

    @Transactional
    Cidade adicionar(Cidade cidade);

    Cidade porId(Long id);

    @Transactional
    void remover(Cidade cidade);
}
