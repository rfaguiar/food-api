package com.food.api.controller;

import com.food.api.model.request.GrupoRequest;
import com.food.api.model.response.GrupoResponse;
import com.food.service.GrupoService;
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
@RequestMapping("/grupos")
public class GrupoController {

    private final GrupoService grupoService;

    @Autowired
    public GrupoController(GrupoService grupoService) {
        this.grupoService = grupoService;
    }

    @GetMapping
    public List<GrupoResponse> listar() {
        return grupoService.listar();
    }

    @GetMapping("/{grupoId}")
    public GrupoResponse buscar(@PathVariable Long grupoId) {
        return grupoService.buscar(grupoId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoResponse cadastrar(@RequestBody @Valid GrupoRequest grupo) {
        return grupoService.cadastrar(grupo);
    }

    @PutMapping("/{grupoId}")
    public GrupoResponse atualizar(@PathVariable Long grupoId,
                                   @RequestBody @Valid GrupoRequest dto) {
        return grupoService.atualizar(grupoId, dto);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId) {
        grupoService.remover(grupoId);
    }
}

