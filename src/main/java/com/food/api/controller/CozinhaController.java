package com.food.api.controller;

import com.food.service.CozinhaService;
import com.food.service.model.CozinhaDto;
import org.springframework.beans.factory.annotation.Autowired;
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
                                @Valid
                                CozinhaDto cozinha) {
        return cozinhaService.salvar(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public CozinhaDto atualizar(@PathVariable Long cozinhaId,
                                @RequestBody @Valid CozinhaDto cozinha) {
        return cozinhaService.atualizar(cozinhaId, cozinha);
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        cozinhaService.remover(cozinhaId);
    }
}
