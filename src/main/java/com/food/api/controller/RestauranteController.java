package com.food.api.controller;

import com.food.service.RestauranteService;
import com.food.service.model.RestauranteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public List<RestauranteDto> listar() {
        return restauranteService.todos();
    }

    @GetMapping("/{restauranteId}")
    public RestauranteDto porId(@PathVariable Long restauranteId) {
        return restauranteService.buscarPorId(restauranteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDto adicionar(@RequestBody
                                    @Valid
                                    RestauranteDto restaurante) {
        return restauranteService.adicionar(restaurante);
    }

    @PutMapping("/{restauranteId}")
    public RestauranteDto atualizar(@PathVariable Long restauranteId,
                                    @RequestBody @Valid RestauranteDto restaurante) {
        return restauranteService.atualizar(restauranteId, restaurante);
    }

    @PatchMapping("/{restauranteId}")
    public RestauranteDto atualizarParcial(@PathVariable Long restauranteId,
                                           @RequestBody Map<String, Object> campos, HttpServletRequest request) {
        return restauranteService.atualizarParcial(restauranteId, campos, request);
    }
}
