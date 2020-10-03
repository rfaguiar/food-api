package com.food.api.controller;

import com.food.api.model.request.RestauranteRequest;
import com.food.api.model.response.RestauranteResponse;
import com.food.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final RestauranteService restauranteService;

    @Autowired
    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @GetMapping
    public List<RestauranteResponse> listar() {
        return restauranteService.todos();
    }

    @GetMapping("/{restauranteId}")
    public RestauranteResponse porId(@PathVariable Long restauranteId) {
        return restauranteService.buscarPorId(restauranteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteResponse adicionar(@RequestBody
                                    @Valid
                                    RestauranteRequest restaurante) {
        return restauranteService.adicionar(restaurante);
    }

    @PutMapping("/{restauranteId}")
    public RestauranteResponse atualizar(@PathVariable Long restauranteId,
                                         @RequestBody @Valid RestauranteRequest restaurante) {
        return restauranteService.atualizar(restauranteId, restaurante);
    }

    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId) {
        restauranteService.ativar(restauranteId);
    }

    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long restauranteId) {
        restauranteService.inativar(restauranteId);
    }

    @PatchMapping("/{restauranteId}")
    public RestauranteResponse atualizarParcial(@PathVariable Long restauranteId,
                                                @RequestBody Map<String, Object> campos, HttpServletRequest request) {
        return restauranteService.atualizarParcial(restauranteId, campos, request);
    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrir(@PathVariable Long restauranteId) {
        restauranteService.abrir(restauranteId);
    }

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long restauranteId) {
        restauranteService.fechar(restauranteId);
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restaurantesIds) {
        restauranteService.ativar(restaurantesIds);
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> restaurantesIds) {
        restauranteService.inativar(restaurantesIds);
    }
}
