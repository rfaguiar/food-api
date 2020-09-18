package com.food.service;

import com.food.api.model.request.UsuarioComSenhaRequest;
import com.food.api.model.request.UsuarioSemSenhaRequest;
import com.food.api.model.response.GrupoResponse;
import com.food.api.model.response.UsuarioResponse;

import java.util.List;

public interface UsuarioService {
    List<UsuarioResponse> listar();

    UsuarioResponse buscar(Long id);

    UsuarioResponse cadastrar(UsuarioComSenhaRequest usuario);

    UsuarioResponse atualizar(Long id, UsuarioSemSenhaRequest usuario);

    void alterarSenha(Long id, String senhaAtual, String novaSenha);

    List<GrupoResponse> buscarGruposPorUsuarioId(Long usuarioId);

    void desassociarGrupo(Long usuarioId, Long grupoId);

    void associarGrupo(Long usuarioId, Long grupoId);
}
