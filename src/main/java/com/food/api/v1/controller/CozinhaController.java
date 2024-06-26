package com.food.api.v1.controller;

import com.food.api.security.CheckSecurity;
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
import org.springframework.web.bind.annotation.*;

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

    @Override
    @CheckSecurity.Cozinhas.PodeConsultar
    @GetMapping
    public PagedModel<CozinhaResponse> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<Cozinha> cozinhasPaged = cozinhaService.todas(pageable);
        return pagedResourcesAssembler.toModel(cozinhasPaged, cozinhaResponseAssembler);
    }

    @Override
    @CheckSecurity.Cozinhas.PodeConsultar
    @GetMapping("/{cozinhaId}")
    public CozinhaResponse porId(@PathVariable Long cozinhaId) {
        return cozinhaResponseAssembler.toModel(cozinhaService.buscarPorId(cozinhaId));
    }

    @Override
    @CheckSecurity.Cozinhas.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaResponse adicionar(@RequestBody
                                     @Valid
                                     CozinhaRequest cozinha) {
        return cozinhaResponseAssembler.toModel(cozinhaService.salvar(cozinha));
    }

    @Override
    @CheckSecurity.Cozinhas.PodeEditar
    @PutMapping("/{cozinhaId}")
    public CozinhaResponse atualizar(@PathVariable Long cozinhaId,
                                     @RequestBody @Valid CozinhaRequest cozinha) {
        return cozinhaResponseAssembler.toModel(cozinhaService.atualizar(cozinhaId, cozinha));
    }

    @Override
    @CheckSecurity.Cozinhas.PodeEditar
    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        cozinhaService.remover(cozinhaId);
    }
}
