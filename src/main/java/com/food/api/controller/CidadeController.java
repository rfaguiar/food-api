package com.food.api.controller;

import com.food.api.model.request.CidadeRequest;
import com.food.api.model.response.CidadeResponse;
import com.food.config.OpenApiConfig;
import com.food.service.CidadeService;
import io.swagger.annotations.Api;
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

@Api(tags = OpenApiConfig.TAG_CIDADE)
@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    private final CidadeService cidadeService;

    @Autowired
    public CidadeController(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }

    @GetMapping
    public List<CidadeResponse> listar() {
        return cidadeService.todos();
    }

    @GetMapping("/{cidadeId}")
    public CidadeResponse porId(@PathVariable Long cidadeId) {
        return cidadeService.buscarPorId(cidadeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeResponse adicionar(@RequestBody @Valid CidadeRequest cidade) {
        return cidadeService.adicionar(cidade);
    }

    @PutMapping("/{cidadeId}")
    public CidadeResponse atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeRequest cidade) {
        return cidadeService.atualizar(cidadeId, cidade);
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cidadeService.remover(cidadeId);
    }
}
