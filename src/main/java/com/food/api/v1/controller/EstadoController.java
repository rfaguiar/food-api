package com.food.api.v1.controller;

import com.food.api.v1.assembler.EstadoResponseAssembler;
import com.food.api.v1.model.request.EstadoRequest;
import com.food.api.v1.model.response.EstadoResponse;
import com.food.api.v1.openapi.controller.EstadoControllerOpenApi;
import com.food.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

@RestController
@RequestMapping(value = "/estados")
public class EstadoController implements EstadoControllerOpenApi {

    private final EstadoService estadoService;
    private final EstadoResponseAssembler estadoResponseAssembler;

    @Autowired
    public EstadoController(EstadoService estadoService, EstadoResponseAssembler estadoResponseAssembler) {
        this.estadoService = estadoService;
        this.estadoResponseAssembler = estadoResponseAssembler;
    }

    @Override
    @GetMapping
    public CollectionModel<EstadoResponse> listar() {
        return estadoResponseAssembler.toCollectionModel(estadoService.todos());
    }

    @Override
    @GetMapping("/{estadoId}")
    public EstadoResponse porId(@PathVariable Long estadoId) {
        return estadoResponseAssembler.toModel(estadoService.buscarPorId(estadoId));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoResponse adicionar(@RequestBody @Valid EstadoRequest estado) {
        return estadoResponseAssembler.toModel(estadoService.adicionar(estado));
    }

    @Override
    @PutMapping("/{estadoId}")
    public EstadoResponse atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoRequest estado) {
        return estadoResponseAssembler.toModel(estadoService.atualizar(estadoId, estado));
    }

    @Override
    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        estadoService.remover(estadoId);
    }
}
