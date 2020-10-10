package com.food.api.controller;

import com.food.api.assembler.UsuarioResponseAssembler;
import com.food.api.model.response.UsuarioResponse;
import com.food.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.food.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

    private final RestauranteService restauranteService;
    private final UsuarioResponseAssembler usuarioResponseAssembler;

    @Autowired
    public RestauranteUsuarioResponsavelController(RestauranteService restauranteService, UsuarioResponseAssembler usuarioResponseAssembler) {
        this.restauranteService = restauranteService;
        this.usuarioResponseAssembler = usuarioResponseAssembler;
    }

    @Override
    @GetMapping
    public CollectionModel<UsuarioResponse> listar(@PathVariable Long restauranteId) {
        return usuarioResponseAssembler.toCollectionModel(restauranteService.buscarUsuariosPorRestauranteId(restauranteId));
    }

    @Override
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.desassociarResponsavel(restauranteId, usuarioId);
    }

    @Override
    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.associarResponsavel(restauranteId, usuarioId);
    }
}
