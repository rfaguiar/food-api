package com.food.api.controller;

import com.food.api.model.request.CozinhaRequest;
import com.food.api.model.response.CozinhaResponse;
import com.food.api.openapi.controller.CozinhaControllerOpenApi;
import com.food.service.CozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping("/cozinhas")
public class CozinhaController implements CozinhaControllerOpenApi {

    private final CozinhaService cozinhaService;

    @Autowired
    public CozinhaController(CozinhaService cozinhaService) {
        this.cozinhaService = cozinhaService;
    }

    @Override
    @GetMapping
    public Page<CozinhaResponse> listar(@PageableDefault(size = 2) Pageable pageable) {
        return cozinhaService.todas(pageable);
    }

    @Override
    @GetMapping("/{cozinhaId}")
    public CozinhaResponse portId(@PathVariable Long cozinhaId) {
        return cozinhaService.buscarPorId(cozinhaId);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaResponse adicionar(@RequestBody
                                     @Valid
                                     CozinhaRequest cozinha) {
        return cozinhaService.salvar(cozinha);
    }

    @Override
    @PutMapping("/{cozinhaId}")
    public CozinhaResponse atualizar(@PathVariable Long cozinhaId,
                                     @RequestBody @Valid CozinhaRequest cozinha) {
        return cozinhaService.atualizar(cozinhaId, cozinha);
    }

    @Override
    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        cozinhaService.remover(cozinhaId);
    }
}
