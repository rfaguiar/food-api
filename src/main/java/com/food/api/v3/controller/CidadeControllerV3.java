package com.food.api.v3.controller;

import com.food.api.helper.ResourceUriHelper;
import com.food.api.v1.model.request.CidadeRequest;
import com.food.api.v1.model.request.EstadoIdRequest;
import com.food.api.v3.openapi.controller.CidadeControllerV3OpenApi;
import com.food.api.v3.assembler.CidadeResponseAssemblerV3;
import com.food.api.v3.model.request.CidadeRequestV3;
import com.food.api.v3.model.response.CidadeResponseV3;
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

@Deprecated
@RestController
@RequestMapping(value = "/v3/cidades")
public class CidadeControllerV3 implements CidadeControllerV3OpenApi {

    private final CidadeService cidadeService;
    private final CidadeResponseAssemblerV3 cidadeResponseAssembler;

    @Autowired
    public CidadeControllerV3(CidadeService cidadeService, CidadeResponseAssemblerV3 cidadeResponseAssembler) {
        this.cidadeService = cidadeService;
        this.cidadeResponseAssembler = cidadeResponseAssembler;
    }

    @Deprecated
    @Override
    @GetMapping
    public CollectionModel<CidadeResponseV3> listar() {
        return cidadeResponseAssembler.toCollectionModel(cidadeService.todos());
    }

    @Deprecated
    @Override
    @GetMapping("/{cidadeId}")
    public CidadeResponseV3 porId(@PathVariable Long cidadeId) {
        return cidadeResponseAssembler.toModel(cidadeService.buscarPorId(cidadeId));
    }

    @Deprecated
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeResponseV3 adicionar(@RequestBody @Valid CidadeRequestV3 cidadeV2) {
        CidadeRequest cidadeRequest = new CidadeRequest(cidadeV2.nomeCidade(),
                new EstadoIdRequest(cidadeV2.idEstado()));
        Cidade novaCidade = cidadeService.adicionar(cidadeRequest);
        ResourceUriHelper.addUriInResponseHeader(novaCidade.getId());
        return cidadeResponseAssembler.toModel(novaCidade);
    }

    @Deprecated
    @Override
    @PutMapping("/{cidadeId}")
    public CidadeResponseV3 atualizar(@PathVariable Long cidadeId,
                                      @RequestBody @Valid CidadeRequestV3 cidadeV2) {
        CidadeRequest cidadeRequest = new CidadeRequest(cidadeV2.nomeCidade(),
                new EstadoIdRequest(cidadeV2.idEstado()));
        return cidadeResponseAssembler.toModel(cidadeService.atualizar(cidadeId, cidadeRequest));
    }

    @Deprecated
    @Override
    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cidadeService.remover(cidadeId);
    }
}
