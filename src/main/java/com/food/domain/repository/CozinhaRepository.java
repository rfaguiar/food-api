package com.food.domain.repository;

import com.food.domain.model.Cozinha;

import javax.transaction.Transactional;
import java.util.List;

public interface CozinhaRepository {
    List<Cozinha> todas();
    Cozinha adicionar(Cozinha cozinha);
    Cozinha porId(Long idCozinha);
    void remover(Cozinha cozinha);
}
