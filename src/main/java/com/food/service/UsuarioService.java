package com.food.service;

import com.food.api.v1.model.request.UsuarioComSenhaRequest;
import com.food.api.v1.model.request.UsuarioSemSenhaRequest;
import com.food.domain.model.Grupo;
import com.food.domain.model.Usuario;

import java.util.List;

public interface UsuarioService {
    List<Usuario> listar();

    Usuario buscar(Long id);

    Usuario cadastrar(UsuarioComSenhaRequest usuario);

    Usuario atualizar(Long id, UsuarioSemSenhaRequest usuario);

    void alterarSenha(Long id, String senhaAtual, String novaSenha);

    List<Grupo> buscarGruposPorUsuarioId(Long usuarioId);

    void desassociarGrupo(Long usuarioId, Long grupoId);

    void associarGrupo(Long usuarioId, Long grupoId);
}
