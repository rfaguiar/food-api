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
import org.springframework.web.bind.annotation.*;

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

    @Deprecated
    @Override
    @GetMapping
    public PagedModel<CozinhaResponseV2> listar(@PageableDefault(size = 2) Pageable pageable) {
        Page<Cozinha> cozinhasPaged = cozinhaService.todas(pageable);
        return pagedResourcesAssembler.toModel(cozinhasPaged, cozinhaResponseAssembler);
    }

    @Deprecated
    @Override
    @GetMapping("/{cozinhaId}")
    public CozinhaResponseV2 porId(@PathVariable Long cozinhaId) {
        return cozinhaResponseAssembler.toModel(cozinhaService.buscarPorId(cozinhaId));
    }

    @Deprecated
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

    @Deprecated
    @Override
    @PutMapping("/{cozinhaId}")
    public CozinhaResponseV2 atualizar(@PathVariable Long cozinhaId,
                                       @RequestBody @Valid CozinhaRequestV2 cozinhaV2) {
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
