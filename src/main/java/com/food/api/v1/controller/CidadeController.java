package com.food.api.v1.controller;

import com.food.api.helper.ResourceUriHelper;
import com.food.api.security.CheckSecurity;
import com.food.api.v1.assembler.CidadeResponseAssembler;
import com.food.api.v1.model.request.CidadeRequest;
import com.food.api.v1.model.response.CidadeResponse;
import com.food.api.v1.openapi.controller.CidadeControllerOpenApi;
import com.food.domain.model.Cidade;
import com.food.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/cidades")
public class CidadeController implements CidadeControllerOpenApi {

    private final CidadeService cidadeService;
    private final CidadeResponseAssembler cidadeResponseAssembler;

    @Autowired
    public CidadeController(CidadeService cidadeService, CidadeResponseAssembler cidadeResponseAssembler) {
        this.cidadeService = cidadeService;
        this.cidadeResponseAssembler = cidadeResponseAssembler;
    }

    @CheckSecurity.Cidades.PodeConsultar
    @GetMapping
    public CollectionModel<CidadeResponse> listar() {
        return cidadeResponseAssembler.toCollectionModel(cidadeService.todos());
    }

    @CheckSecurity.Cidades.PodeConsultar
    @GetMapping("/{cidadeId}")
    public CidadeResponse porId(@PathVariable Long cidadeId) {
        return cidadeResponseAssembler.toModel(cidadeService.buscarPorId(cidadeId));
    }

    @CheckSecurity.Cidades.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeResponse adicionar(@RequestBody @Valid CidadeRequest cidade) {
        Cidade novaCidade = cidadeService.adicionar(cidade);
        ResourceUriHelper.addUriInResponseHeader(novaCidade.getId());
        return cidadeResponseAssembler.toModel(novaCidade);
    }

    @CheckSecurity.Cidades.PodeEditar
    @PutMapping("/{cidadeId}")
    public CidadeResponse atualizar(@PathVariable Long cidadeId,
                                    @RequestBody @Valid CidadeRequest cidade) {
        return cidadeResponseAssembler.toModel(cidadeService.atualizar(cidadeId, cidade));
    }

    @CheckSecurity.Cidades.PodeEditar
    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cidadeService.remover(cidadeId);
    }
}
