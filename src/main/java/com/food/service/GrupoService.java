package com.food.service;

import com.food.api.model.request.GrupoRequest;
import com.food.api.model.response.GrupoResponse;
import com.food.api.model.response.PermissaoResponse;

import java.util.List;

public interface GrupoService {
    List<GrupoResponse> listar();

    GrupoResponse buscar(Long id);

    GrupoResponse cadastrar(GrupoRequest grupo);

    GrupoResponse atualizar(Long id, GrupoRequest dto);

    void remover(Long id);

    void desassociarPermissao(Long grupoId, Long permissaoId);

    List<PermissaoResponse> buscarPermissoesOuFalhar(Long grupoId);
}
