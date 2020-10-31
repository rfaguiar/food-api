package com.food.api.v2.controller;

import com.food.api.helper.ResourceUriHelper;
import com.food.api.v1.model.request.CidadeRequest;
import com.food.api.v1.model.request.EstadoIdRequest;
import com.food.api.v2.assembler.CidadeResponseAssemblerV2;
import com.food.api.v2.model.request.CidadeRequestV2;
import com.food.api.v2.model.response.CidadeResponseV2;
import com.food.api.v2.openapi.controller.CidadeControllerV2OpenApi;
import com.food.domain.model.Cidade;
import com.food.service.CidadeService;
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
@RequestMapping(value = "/v2/cidades")
public class CidadeControllerV2 implements CidadeControllerV2OpenApi {

    private final CidadeService cidadeService;
    private final CidadeResponseAssemblerV2 cidadeResponseAssembler;

    @Autowired
    public CidadeControllerV2(CidadeService cidadeService, CidadeResponseAssemblerV2 cidadeResponseAssembler) {
        this.cidadeService = cidadeService;
        this.cidadeResponseAssembler = cidadeResponseAssembler;
    }

    @Override
    @GetMapping
    public CollectionModel<CidadeResponseV2> listar() {
        return cidadeResponseAssembler.toCollectionModel(cidadeService.todos());
    }

    @Override
    @GetMapping("/{cidadeId}")
    public CidadeResponseV2 porId(@PathVariable Long cidadeId) {
        return cidadeResponseAssembler.toModel(cidadeService.buscarPorId(cidadeId));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeResponseV2 adicionar(@RequestBody @Valid CidadeRequestV2 cidadeV2) {
        CidadeRequest cidadeRequest = new CidadeRequest(cidadeV2.nomeCidade(),
                new EstadoIdRequest(cidadeV2.idEstado()));
        Cidade novaCidade = cidadeService.adicionar(cidadeRequest);
        ResourceUriHelper.addUriInResponseHeader(novaCidade.getId());
        return cidadeResponseAssembler.toModel(novaCidade);
    }

    @Override
    @PutMapping("/{cidadeId}")
    public CidadeResponseV2 atualizar(@PathVariable Long cidadeId,
                                      @RequestBody @Valid CidadeRequestV2 cidadeV2) {
        CidadeRequest cidadeRequest = new CidadeRequest(cidadeV2.nomeCidade(),
                new EstadoIdRequest(cidadeV2.idEstado()));
        return cidadeResponseAssembler.toModel(cidadeService.atualizar(cidadeId, cidadeRequest));
    }

    @Override
    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cidadeService.remover(cidadeId);
    }
}
