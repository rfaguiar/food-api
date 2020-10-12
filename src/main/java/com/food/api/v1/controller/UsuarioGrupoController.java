package com.food.api.v1.controller;

import com.food.api.v1.assembler.FoodLinks;
import com.food.api.v1.assembler.GrupoResponseAssembler;
import com.food.api.v1.model.response.GrupoResponse;
import com.food.api.v1.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.food.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

    private final UsuarioService usuarioService;
    private final GrupoResponseAssembler grupoResponseAssembler;
    private final FoodLinks foodLinks;

    @Autowired
    public UsuarioGrupoController(UsuarioService usuarioService, GrupoResponseAssembler grupoResponseAssembler, FoodLinks foodLinks) {
        this.usuarioService = usuarioService;
        this.grupoResponseAssembler = grupoResponseAssembler;
        this.foodLinks = foodLinks;
    }

    @Override
    @GetMapping
    public CollectionModel<GrupoResponse> listar(@PathVariable Long usuarioId) {
        CollectionModel<GrupoResponse> gruposResponse = grupoResponseAssembler.toCollectionModel(usuarioService.buscarGruposPorUsuarioId(usuarioId))
                .removeLinks()
                .add(foodLinks.linkToUsuarioGrupoAssociacao(usuarioId, "associar"));
        gruposResponse.getContent().forEach(grupoModel -> {
            grupoModel.add(foodLinks.linkToUsuarioGrupoDesassociacao(
                    usuarioId, grupoModel.getId(), "desassociar"));
        });
        return gruposResponse;
    }

    @Override
    @DeleteMapping("/{grupoId}")
    public ResponseEntity<Void> desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.desassociarGrupo(usuarioId, grupoId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{grupoId}")
    public ResponseEntity<Void> associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.associarGrupo(usuarioId, grupoId);
        return ResponseEntity.noContent().build();
    }
}
