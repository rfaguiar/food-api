package com.food.api.controller;

import com.food.api.model.response.PermissaoResponse;
import com.food.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

    private final GrupoService grupoService;

    @Autowired
    public GrupoPermissaoController(GrupoService grupoService) {
        this.grupoService = grupoService;
    }

    @GetMapping
    public List<PermissaoResponse> listar(@PathVariable Long grupoId) {
        return grupoService.buscarPermissoesOuFalhar(grupoId);
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.desassociarPermissao(grupoId, permissaoId);
    }
}
