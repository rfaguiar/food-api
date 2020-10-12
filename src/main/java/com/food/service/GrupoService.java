package com.food.service;

import com.food.api.model.request.GrupoRequest;
import com.food.domain.model.Grupo;
import com.food.domain.model.Permissao;

import java.util.List;

public interface GrupoService {
    List<Grupo> listar();

    Grupo buscar(Long id);

    Grupo cadastrar(GrupoRequest grupo);

    Grupo atualizar(Long id, GrupoRequest dto);

    void remover(Long id);

    void desassociarPermissao(Long grupoId, Long permissaoId);

    List<Permissao> buscarPermissoesOuFalhar(Long grupoId);

    void associarPermissao(Long grupoId, Long permissaoId);
}
