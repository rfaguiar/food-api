package com.food.infrastructure.service;

import com.food.api.model.request.GrupoRequest;
import com.food.api.model.response.GrupoResponse;
import com.food.domain.exception.GrupoNaoEncontradoException;
import com.food.domain.model.Grupo;
import com.food.domain.repository.GrupoRepository;
import com.food.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GrupoServiceImpl implements GrupoService {

    private final GrupoRepository grupoRepository;

    @Autowired
    public GrupoServiceImpl(GrupoRepository grupoRepository) {
        this.grupoRepository = grupoRepository;
    }

    @Override
    public List<GrupoResponse> listar() {
        return grupoRepository.findAll()
                .stream()
                .map(GrupoResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public GrupoResponse buscar(Long id) {
        Grupo grupo = buscarEValidarGrupo(id);
        return new GrupoResponse(grupo);
    }

    @Override
    public GrupoResponse cadastrar(GrupoRequest dto) {
        Grupo grupo = grupoRepository.save(new Grupo(null, dto.nome(), null));
        return new GrupoResponse(grupo);
    }

    @Override
    public GrupoResponse atualizar(Long id, GrupoRequest dto) {
        Grupo grupoAntigo = buscarEValidarGrupo(id);
        Grupo grupo = grupoRepository.save(new Grupo(grupoAntigo.id(), dto.nome(), null));
        return new GrupoResponse(grupo);
    }

    @Override
    public void remover(Long id) {
        Grupo grupo = buscarEValidarGrupo(id);
        grupoRepository.delete(grupo);
    }

    private Grupo buscarEValidarGrupo(Long id) {
        return grupoRepository.findById(id)
                .orElseThrow(() -> new GrupoNaoEncontradoException(id));
    }
}
