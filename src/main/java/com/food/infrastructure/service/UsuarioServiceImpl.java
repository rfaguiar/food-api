package com.food.infrastructure.service;

import com.food.api.v1.model.request.UsuarioComSenhaRequest;
import com.food.api.v1.model.request.UsuarioSemSenhaRequest;
import com.food.domain.exception.NegocioException;
import com.food.domain.exception.UsuarioNaoEncontradoException;
import com.food.domain.model.Grupo;
import com.food.domain.model.Usuario;
import com.food.domain.repository.UsuarioRepository;
import com.food.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final GrupoServiceImpl grupoService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, GrupoServiceImpl grupoService, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.grupoService = grupoService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario buscar(Long id) {
        return buscarEValidarPorId(id);
    }

    @Override
    @Transactional
    public Usuario cadastrar(UsuarioComSenhaRequest usuario) {
        validarPorEmail(usuario.email(), usuario.nome());
        return usuarioRepository.save(new Usuario(null,
                usuario.nome(),
                usuario.email(),
                passwordEncoder.encode(usuario.senha()),
                null,
                null));
    }

    @Override
    @Transactional
    public Usuario atualizar(Long id, UsuarioSemSenhaRequest usuario) {
        validarPorEmail(usuario.email(), usuario.nome());
        Usuario antigo = buscarEValidarPorId(id);
        return usuarioRepository.save(new Usuario(
                antigo.id(),
                usuario.nome(),
                usuario.email(),
                antigo.senha(),
                antigo.dataCadastro(),
                antigo.grupos()));
    }

    @Override
    public void alterarSenha(Long id, String senhaAtual, String novaSenha) {
        Usuario antigo = buscarEValidarPorId(id);
        if (!passwordEncoder.matches(senhaAtual, antigo.senha())) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }
        usuarioRepository.save(new Usuario(
                antigo.id(),
                antigo.nome(),
                antigo.email(),
                passwordEncoder.encode(novaSenha),
                antigo.dataCadastro(),
                antigo.grupos()));
    }

    @Override
    public List<Grupo> buscarGruposPorUsuarioId(Long usuarioId) {
        Usuario usuario = usuarioRepository.findUsuarioWithGrupos(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
        return new ArrayList<>(usuario.grupos());
    }

    @Override
    @Transactional
    public void desassociarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscarEValidarPorId(usuarioId);
        Grupo grupo = grupoService.buscarEValidarGrupo(grupoId);
        usuario.removerGrupo(grupo);
    }

    @Override
    @Transactional
    public void associarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscarEValidarPorId(usuarioId);
        Grupo grupo = grupoService.buscarEValidarGrupo(grupoId);
        usuario.adicionarGrupo(grupo);
    }

    private void validarPorEmail(String email, String nome) {
        Optional<Usuario> byEmail = usuarioRepository.findByEmail(email);
        if(byEmail.isPresent() && nome.equals(byEmail.get().nome())) {
            throw new NegocioException(MessageFormat.format(
                    "Já existe um usúario cadastrado com o e-mail {0}", email));
        }
    }

    Usuario buscarEValidarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }
}
