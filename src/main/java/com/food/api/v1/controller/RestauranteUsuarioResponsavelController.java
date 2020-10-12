package com.food.api.v1.controller;

import com.food.api.v1.assembler.FoodLinks;
import com.food.api.v1.assembler.UsuarioResponseAssembler;
import com.food.api.v1.model.response.UsuarioResponse;
import com.food.api.v1.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.food.service.RestauranteService;
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
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

    private final RestauranteService restauranteService;
    private final UsuarioResponseAssembler usuarioResponseAssembler;
    private final FoodLinks foodLinks;

    @Autowired
    public RestauranteUsuarioResponsavelController(RestauranteService restauranteService, UsuarioResponseAssembler usuarioResponseAssembler, FoodLinks foodLinks) {
        this.restauranteService = restauranteService;
        this.usuarioResponseAssembler = usuarioResponseAssembler;
        this.foodLinks = foodLinks;
    }

    @Override
    @GetMapping
    public CollectionModel<UsuarioResponse> listar(@PathVariable Long restauranteId) {
        CollectionModel<UsuarioResponse> usuariosResponse = usuarioResponseAssembler.toCollectionModel(restauranteService.buscarUsuariosPorRestauranteId(restauranteId))
                .removeLinks()
                .add(foodLinks.linkToResponsaveisRestaurante(restauranteId))
                .add(foodLinks.linkToRestauranteResponsavelAssociacao(restauranteId, "associar"));
        usuariosResponse.getContent().forEach(usuario -> usuario.add(
                foodLinks.linkToRestauranteResponsavelDesassociacao(restauranteId, usuario.getId(), "desassociar")));
        return usuariosResponse;
    }

    @Override
    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.desassociarResponsavel(restauranteId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{usuarioId}")
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.associarResponsavel(restauranteId, usuarioId);
        return ResponseEntity.noContent().build();
    }
}
