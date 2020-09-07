package com.food.api.controller;

import com.food.service.CozinhaService;
import com.food.service.model.CozinhaDto;
import com.food.service.validation.CadastroCozinhaGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    private final CozinhaService cozinhaService;

    @Autowired
    public CozinhaController(CozinhaService cozinhaService) {
        this.cozinhaService = cozinhaService;
    }

    @GetMapping
    public List<CozinhaDto> listar() {
        return cozinhaService.todas();
    }

    @GetMapping("/{cozinhaId}")
    public CozinhaDto portId(@PathVariable Long cozinhaId) {
        return cozinhaService.buscarPorId(cozinhaId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDto adicionar(@RequestBody
                                @Validated(value = CadastroCozinhaGroup.class)
                                CozinhaDto cozinha) {
        return cozinhaService.salvar(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public CozinhaDto atualizar(@PathVariable Long cozinhaId,
                                @RequestBody CozinhaDto cozinha) {
        return cozinhaService.atualizar(cozinhaId, cozinha);
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        cozinhaService.remover(cozinhaId);
    }
}
