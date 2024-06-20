package com.food.api.v1.controller;

import com.food.api.security.CheckSecurity;
import com.food.api.v1.assembler.RestauranteApenasNomeResponseAssembler;
import com.food.api.v1.assembler.RestauranteBasicoResponseAssembler;
import com.food.api.v1.assembler.RestauranteResponseAssembler;
import com.food.api.v1.model.request.RestauranteRequest;
import com.food.api.v1.model.response.RestauranteApenasNomeResponse;
import com.food.api.v1.model.response.RestauranteBasicoResponse;
import com.food.api.v1.model.response.RestauranteResponse;
import com.food.api.v1.openapi.controller.RestauranteControllerOpenApi;
import com.food.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/restaurantes")
public class RestauranteController implements RestauranteControllerOpenApi {

    private final RestauranteService restauranteService;
    private final RestauranteResponseAssembler restauranteResponseAssembler;
    private final RestauranteApenasNomeResponseAssembler restauranteApenasNomeResponseAssembler;
    private final RestauranteBasicoResponseAssembler restauranteBasicoResponseAssembler;

    @Autowired
    public RestauranteController(RestauranteService restauranteService, RestauranteResponseAssembler restauranteResponseAssembler, RestauranteApenasNomeResponseAssembler restauranteApenasNomeResponseAssembler, RestauranteBasicoResponseAssembler restauranteBasicoResponseAssembler) {
        this.restauranteService = restauranteService;
        this.restauranteResponseAssembler = restauranteResponseAssembler;
        this.restauranteApenasNomeResponseAssembler = restauranteApenasNomeResponseAssembler;
        this.restauranteBasicoResponseAssembler = restauranteBasicoResponseAssembler;
    }

    @Override
    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping
    public CollectionModel<RestauranteBasicoResponse> listar() {
        return restauranteBasicoResponseAssembler.toCollectionModel(restauranteService.todos());
    }

    @Override
    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping(params = "projecao=apenas-nome")
    public CollectionModel<RestauranteApenasNomeResponse> listarApenasNomes() {
        return restauranteApenasNomeResponseAssembler.toCollectionModel(restauranteService.todos());
    }

    @Override
    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping("/{restauranteId}")
    public RestauranteResponse porId(@PathVariable Long restauranteId) {
        return restauranteResponseAssembler.toModel(restauranteService.buscarPorId(restauranteId));
    }

    @Override
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteResponse adicionar(@RequestBody
                                         @Valid
                                                 RestauranteRequest restaurante) {
        return restauranteResponseAssembler.toModel(restauranteService.adicionar(restaurante));
    }

    @Override
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("/{restauranteId}")
    public RestauranteResponse atualizar(@PathVariable Long restauranteId,
                                         @RequestBody @Valid RestauranteRequest restaurante) {
        return restauranteResponseAssembler.toModel(restauranteService.atualizar(restauranteId, restaurante));
    }

    @Override
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PatchMapping("/{restauranteId}")
    public RestauranteResponse atualizarParcial(@PathVariable Long restauranteId,
                                                @RequestBody Map<String, Object> campos) {
        return restauranteResponseAssembler.toModel(restauranteService.atualizarParcial(restauranteId, campos));
    }

    @Override
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("/{restauranteId}/ativo")
    public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
        restauranteService.ativar(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @DeleteMapping("/{restauranteId}/ativo")
    public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
        restauranteService.inativar(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping("/{restauranteId}/abertura")
    public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
        restauranteService.abrir(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping("/{restauranteId}/fechamento")
    public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
        restauranteService.fechar(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("/ativacoes")
    public ResponseEntity<Void> ativarMultiplos(@RequestBody List<Long> restaurantesIds) {
        restauranteService.ativar(restaurantesIds);
        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @DeleteMapping("/ativacoes")
    public ResponseEntity<Void> inativarMultiplos(@RequestBody List<Long> restaurantesIds) {
        restauranteService.inativar(restaurantesIds);
        return ResponseEntity.noContent().build();
    }
}
