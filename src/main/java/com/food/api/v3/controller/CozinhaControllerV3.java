package com.food.api.v3.controller;

import com.food.api.v1.model.request.CozinhaRequest;
import com.food.api.v3.assembler.CozinhaResponseAssemblerV3;
import com.food.api.v3.model.request.CozinhaRequestV3;
import com.food.api.v3.model.response.CozinhaResponseV3;
import com.food.api.v3.openapi.controller.CozinhaControllerV3OpenApi;
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

@Deprecated
@RestController
@RequestMapping("/v3/cozinhas")
public class CozinhaControllerV3 implements CozinhaControllerV3OpenApi {

    private final CozinhaService cozinhaService;
    private final CozinhaResponseAssemblerV3 cozinhaResponseAssembler;
    private final PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @Autowired
    public CozinhaControllerV3(CozinhaService cozinhaService,
                               CozinhaResponseAssemblerV3 cozinhaResponseAssembler,
                               PagedResourcesAssembler<Cozinha> pagedResourcesAssembler) {
        this.cozinhaService = cozinhaService;
        this.cozinhaResponseAssembler = cozinhaResponseAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @Deprecated
    @Override
    @GetMapping
    public PagedModel<CozinhaResponseV3> listar(@PageableDefault(size = 2) Pageable pageable) {
        Page<Cozinha> cozinhasPaged = cozinhaService.todas(pageable);
        return pagedResourcesAssembler.toModel(cozinhasPaged, cozinhaResponseAssembler);
    }

    @Deprecated
    @Override
    @GetMapping("/{cozinhaId}")
    public CozinhaResponseV3 porId(@PathVariable Long cozinhaId) {
        return cozinhaResponseAssembler.toModel(cozinhaService.buscarPorId(cozinhaId));
    }

    @Deprecated
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaResponseV3 adicionar(
            @RequestBody
            @Valid
                    CozinhaRequestV3 cozinhaV2) {
        CozinhaRequest cozinha = new CozinhaRequest(cozinhaV2.nomeCozinha());
        return cozinhaResponseAssembler.toModel(cozinhaService.salvar(cozinha));
    }

    @Deprecated
    @Override
    @PutMapping("/{cozinhaId}")
    public CozinhaResponseV3 atualizar(@PathVariable Long cozinhaId,
                                       @RequestBody @Valid CozinhaRequestV3 cozinhaV2) {
        CozinhaRequest cozinha = new CozinhaRequest(cozinhaV2.nomeCozinha());
        return cozinhaResponseAssembler.toModel(cozinhaService.atualizar(cozinhaId, cozinha));
    }

    @Deprecated
    @Override
    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        cozinhaService.remover(cozinhaId);
    }
}
