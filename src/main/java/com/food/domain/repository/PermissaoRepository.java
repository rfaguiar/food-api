package com.food.domain.repository;

import com.food.domain.model.Permissao;

import javax.transaction.Transactional;
import java.util.stream.Stream;

public interface PermissaoRepository {
    Stream<Permissao> todas();

    @Transactional
    Permissao adicionar(Permissao Permissao);

    Permissao porId(Long idPermissao);

    @Transactional
    void remover(Permissao Permissao);
}
