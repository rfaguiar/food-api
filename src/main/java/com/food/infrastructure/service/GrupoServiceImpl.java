package com.food.infrastructure.service;

import com.food.api.model.request.GrupoRequest;
import com.food.api.model.response.PermissaoResponse;
import com.food.domain.exception.GrupoNaoEncontradoException;
import com.food.domain.model.Grupo;
import com.food.domain.model.Permissao;
import com.food.domain.repository.GrupoRepository;
import com.food.service.GrupoService;
import com.food.service.PermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GrupoServiceImpl implements GrupoService {

    private final GrupoRepository grupoRepository;
    private final PermissaoService permissaoService;

    @Autowired
    public GrupoServiceImpl(GrupoRepository grupoRepository, PermissaoService permissaoService) {
        this.grupoRepository = grupoRepository;
        this.permissaoService = permissaoService;
    }

    @Override
    public List<Grupo> listar() {
        return grupoRepository.findAll();
    }

    @Override
    public Grupo buscar(Long id) {
        return buscarEValidarGrupo(id);
    }

    @Override
    public Grupo cadastrar(GrupoRequest dto) {
        return grupoRepository.save(new Grupo(null, dto.nome(), null));
    }

    @Override
    public Grupo atualizar(Long id, GrupoRequest dto) {
        Grupo grupoAntigo = buscarEValidarGrupo(id);
        return grupoRepository.save(new Grupo(grupoAntigo.id(), dto.nome(), null));
    }

    @Override
    public void remover(Long id) {
        Grupo grupo = buscarEValidarGrupo(id);
        grupoRepository.delete(grupo);
    }

    @Override
    @Transactional
    public void desassociarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = buscarEValidarGrupo(grupoId);
        Permissao permissao = permissaoService.buscarOuFalhar(permissaoId);
        grupo.removerPermissao(permissao);
    }

    @Transactional
    public void associarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = buscarEValidarGrupo(grupoId);
        Permissao permissao = permissaoService.buscarOuFalhar(permissaoId);
        grupo.adicionarPermissao(permissao);
    }

    @Override
    public List<PermissaoResponse> buscarPermissoesOuFalhar(Long grupoId) {
        Grupo grupo = buscarEValidarGrupo(grupoId);
        return grupo.permissoes()
                .stream()
                .map(PermissaoResponse::new)
                .collect(Collectors.toList());
    }

    public Grupo buscarEValidarGrupo(Long id) {
        return grupoRepository.findById(id)
                .orElseThrow(() -> new GrupoNaoEncontradoException(id));
    }
}
