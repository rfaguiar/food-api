package com.food.domain.repository;

import com.food.domain.model.Cidade;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Stream;

public interface CidadeRepository {
    Stream<Cidade> todas();

    @Transactional
    Cidade adicionar(Cidade cidade);

    Optional<Cidade> porId(Long id);

    @Transactional
    Cidade remover(Cidade cidade);
}
