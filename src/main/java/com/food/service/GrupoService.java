package com.food.service;

import com.food.api.model.request.GrupoRequest;
import com.food.api.model.response.GrupoResponse;

import java.util.List;

public interface GrupoService {
    List<GrupoResponse> listar();

    GrupoResponse buscar(Long id);

    GrupoResponse cadastrar(GrupoRequest grupo);

    GrupoResponse atualizar(Long id, GrupoRequest dto);

    void remover(Long id);
}
