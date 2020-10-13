package com.food.api.v1.controller;

import com.food.api.v1.assembler.CozinhaResponseAssembler;
import com.food.api.v1.model.request.CozinhaRequest;
import com.food.api.v1.model.response.CozinhaResponse;
import com.food.api.v1.openapi.controller.CozinhaControllerOpenApi;
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
@RequestMapping("/v1/cozinhas")
public class CozinhaController implements CozinhaControllerOpenApi {

    private final CozinhaService cozinhaService;
    private final CozinhaResponseAssembler cozinhaResponseAssembler;
    private final PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @Autowired
    public CozinhaController(CozinhaService cozinhaService,
                             CozinhaResponseAssembler cozinhaResponseAssembler,
                             PagedResourcesAssembler<Cozinha> pagedResourcesAssembler) {
        this.cozinhaService = cozinhaService;
        this.cozinhaResponseAssembler = cozinhaResponseAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @Deprecated
    @Override
    @GetMapping
    public PagedModel<CozinhaResponse> listar(@PageableDefault(size = 2) Pageable pageable) {
        Page<Cozinha> cozinhasPaged = cozinhaService.todas(pageable);
        return pagedResourcesAssembler.toModel(cozinhasPaged, cozinhaResponseAssembler);
    }

    @Deprecated
    @Override
    @GetMapping("/{cozinhaId}")
    public CozinhaResponse porId(@PathVariable Long cozinhaId) {
        return cozinhaResponseAssembler.toModel(cozinhaService.buscarPorId(cozinhaId));
    }

    @Deprecated
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaResponse adicionar(@RequestBody
                                     @Valid
                                     CozinhaRequest cozinha) {
        return cozinhaResponseAssembler.toModel(cozinhaService.salvar(cozinha));
    }

    @Deprecated
    @Override
    @PutMapping("/{cozinhaId}")
    public CozinhaResponse atualizar(@PathVariable Long cozinhaId,
                                     @RequestBody @Valid CozinhaRequest cozinha) {
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
