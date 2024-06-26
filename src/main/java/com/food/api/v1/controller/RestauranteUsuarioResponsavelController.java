package com.food.api.v1.controller;

import com.food.api.security.CheckSecurity;
import com.food.api.v1.assembler.FoodLinks;
import com.food.api.v1.assembler.UsuarioResponseAssembler;
import com.food.api.v1.model.response.UsuarioResponse;
import com.food.api.v1.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.food.config.FoodSecurity;
import com.food.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

    private final RestauranteService restauranteService;
    private final UsuarioResponseAssembler usuarioResponseAssembler;
    private final FoodLinks foodLinks;
    private final FoodSecurity foodSecurity;

    @Autowired
    public RestauranteUsuarioResponsavelController(RestauranteService restauranteService, UsuarioResponseAssembler usuarioResponseAssembler, FoodLinks foodLinks, FoodSecurity foodSecurity) {
        this.restauranteService = restauranteService;
        this.usuarioResponseAssembler = usuarioResponseAssembler;
        this.foodLinks = foodLinks;
        this.foodSecurity = foodSecurity;
    }

    @Override
    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping
    public CollectionModel<UsuarioResponse> listar(@PathVariable Long restauranteId) {
        CollectionModel<UsuarioResponse> usuariosResponse = usuarioResponseAssembler.toCollectionModel(restauranteService.buscarUsuariosPorRestauranteId(restauranteId))
                .removeLinks()
                .add(foodLinks.linkToResponsaveisRestaurante(restauranteId));

        if (foodSecurity.podeGerenciarCadastroRestaurantes()) {
            usuariosResponse.add(foodLinks.linkToRestauranteResponsavelAssociacao(restauranteId, "associar"));
            usuariosResponse.getContent().forEach(usuario -> usuario.add(
                    foodLinks.linkToRestauranteResponsavelDesassociacao(restauranteId, usuario.getId(), "desassociar")));
        }
        return usuariosResponse;
    }


    @Override
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.desassociarResponsavel(restauranteId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("/{usuarioId}")
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.associarResponsavel(restauranteId, usuarioId);
        return ResponseEntity.noContent().build();
    }
}
