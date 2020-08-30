package com.food.domain.repository;

import com.food.domain.model.Estado;

import javax.transaction.Transactional;
import java.util.List;

public interface EstadoRepository {
    List<Estado> todos();

    @Transactional
    Estado adicionar(Estado Estado);

    Estado porId(Long id);

    @Transactional
    void remover(Estado Estado);
}
