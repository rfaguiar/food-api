package com.food.infrastructure.service;

import com.food.api.model.request.UsuarioComSenhaRequest;
import com.food.api.model.request.UsuarioSemSenhaRequest;
import com.food.api.model.response.UsuarioResponse;
import com.food.domain.exception.NegocioException;
import com.food.domain.exception.UsuarioNaoEncontradoException;
import com.food.domain.model.Usuario;
import com.food.domain.repository.UsuarioRepository;
import com.food.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<UsuarioResponse> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponse buscar(Long id) {
        Usuario usuario = buscarEValidarPorId(id);
        return new UsuarioResponse(usuario);
    }

    @Override
    public UsuarioResponse cadastrar(UsuarioComSenhaRequest usuario) {
        Usuario novo = usuarioRepository.save(new Usuario(null,
                usuario.nome(),
                usuario.email(),
                usuario.senha(),
                null,
                null));
        return new UsuarioResponse(novo);
    }

    @Override
    public UsuarioResponse atualizar(Long id, UsuarioSemSenhaRequest usuario) {
        Usuario antigo = buscarEValidarPorId(id);
        Usuario novo = usuarioRepository.save(new Usuario(
                antigo.id(),
                usuario.nome(),
                usuario.email(),
                antigo.senha(),
                antigo.dataCadastro(),
                antigo.grupos()));
        return new UsuarioResponse(novo);
    }

    @Override
    public void alterarSenha(Long id, String senhaAtual, String novaSenha) {
        Usuario antigo = buscarEValidarPorId(id);
        if (antigo.senhaNaoCoincideCom(senhaAtual)) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }
        usuarioRepository.save(new Usuario(
                antigo.id(),
                antigo.nome(),
                antigo.email(),
                novaSenha,
                antigo.dataCadastro(),
                antigo.grupos()));
    }

    private Usuario buscarEValidarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }
}
