package com.food.api.v2.controller;

import com.food.api.v1.model.request.CozinhaRequest;
import com.food.api.v2.assembler.CozinhaResponseAssemblerV2;
import com.food.api.v2.model.request.CozinhaRequestV2;
import com.food.api.v2.model.response.CozinhaResponseV2;
import com.food.api.v2.openapi.controller.CozinhaControllerV2OpenApi;
import com.food.domain.model.Cozinha;
import com.food.service.CozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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
@RequestMapping("/v2/cozinhas")
public class CozinhaControllerV2 implements CozinhaControllerV2OpenApi {

    private final CozinhaService cozinhaService;
    private final CozinhaResponseAssemblerV2 cozinhaResponseAssembler;
    private final PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @Autowired
    public CozinhaControllerV2(CozinhaService cozinhaService,
                               CozinhaResponseAssemblerV2 cozinhaResponseAssembler,
                               PagedResourcesAssembler<Cozinha> pagedResourcesAssembler) {
        this.cozinhaService = cozinhaService;
        this.cozinhaResponseAssembler = cozinhaResponseAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @Override
    @GetMapping
    public PagedModel<CozinhaResponseV2> listar(@PageableDefault(size = 2) Pageable pageable) {
        Page<Cozinha> cozinhasPaged = cozinhaService.todas(pageable);
        return pagedResourcesAssembler.toModel(cozinhasPaged, cozinhaResponseAssembler);
    }

    @Override
    @GetMapping("/{cozinhaId}")
    public CozinhaResponseV2 porId(@PathVariable Long cozinhaId) {
        return cozinhaResponseAssembler.toModel(cozinhaService.buscarPorId(cozinhaId));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaResponseV2 adicionar(
            @RequestBody
            @Valid
                    CozinhaRequestV2 cozinhaV2) {
        CozinhaRequest cozinha = new CozinhaRequest(cozinhaV2.nomeCozinha());
        return cozinhaResponseAssembler.toModel(cozinhaService.salvar(cozinha));
    }

    @Override
    @PutMapping("/{cozinhaId}")
    public CozinhaResponseV2 atualizar(@PathVariable Long cozinhaId,
                                       @RequestBody @Valid CozinhaRequestV2 cozinhaV2) {
        CozinhaRequest cozinha = new CozinhaRequest(cozinhaV2.nomeCozinha());
        return cozinhaResponseAssembler.toModel(cozinhaService.atualizar(cozinhaId, cozinha));
    }

    @Override
    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        cozinhaService.remover(cozinhaId);
    }
}
