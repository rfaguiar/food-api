package com.food.api.controller;

import com.food.api.assembler.PermissaoResponseAssembler;
import com.food.api.model.response.PermissaoResponse;
import com.food.api.openapi.controller.PermissaoControllerOpenApi;
import com.food.domain.model.Permissao;
import com.food.domain.repository.PermissaoRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permissoes")
public class PermissaoController implements PermissaoControllerOpenApi {

    private final PermissaoRepository permissaoRepository;
    private final PermissaoResponseAssembler permissaoResponseAssembler;

    public PermissaoController(PermissaoRepository permissaoRepository, PermissaoResponseAssembler permissaoResponseAssembler) {
        this.permissaoRepository = permissaoRepository;
        this.permissaoResponseAssembler = permissaoResponseAssembler;
    }

    @Override
    @GetMapping
    public CollectionModel<PermissaoResponse> listar() {
        List<Permissao> todasPermissoes = permissaoRepository.findAll();
        return permissaoResponseAssembler.toCollectionModel(todasPermissoes);
    }

}
