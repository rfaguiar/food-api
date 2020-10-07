package com.food.api.controller;

import com.food.api.model.request.EstadoRequest;
import com.food.api.model.response.EstadoResponse;
import com.food.api.openapi.controller.EstadoControllerOpenApi;
import com.food.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController implements EstadoControllerOpenApi {

    private final EstadoService estadoService;

    @Autowired
    public EstadoController(EstadoService estadoService) {
        this.estadoService = estadoService;
    }

    @Override
    @GetMapping
    public List<EstadoResponse> listar() {
        return estadoService.todos();
    }

    @Override
    @GetMapping("/{estadoId}")
    public EstadoResponse porId(@PathVariable Long estadoId) {
        return estadoService.buscarPorId(estadoId);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoResponse adicionar(@RequestBody @Valid EstadoRequest estado) {
        return estadoService.adicionar(estado);
    }

    @Override
    @PutMapping("/{estadoId}")
    public EstadoResponse atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoRequest estado) {
        return estadoService.atualizar(estadoId, estado);
    }

    @Override
    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        estadoService.remover(estadoId);
    }
}
