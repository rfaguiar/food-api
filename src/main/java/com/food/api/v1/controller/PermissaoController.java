package com.food.api.v1.controller;

import com.food.api.security.CheckSecurity;
import com.food.api.v1.assembler.PermissaoResponseAssembler;
import com.food.api.v1.model.response.PermissaoResponse;
import com.food.api.v1.openapi.controller.PermissaoControllerOpenApi;
import com.food.domain.model.Permissao;
import com.food.domain.repository.PermissaoRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/permissoes")
public class PermissaoController implements PermissaoControllerOpenApi {

    private final PermissaoRepository permissaoRepository;
    private final PermissaoResponseAssembler permissaoResponseAssembler;

    public PermissaoController(PermissaoRepository permissaoRepository, PermissaoResponseAssembler permissaoResponseAssembler) {
        this.permissaoRepository = permissaoRepository;
        this.permissaoResponseAssembler = permissaoResponseAssembler;
    }

    @Override
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping
    public CollectionModel<PermissaoResponse> listar() {
        List<Permissao> todasPermissoes = permissaoRepository.findAll();
        return permissaoResponseAssembler.toCollectionModel(todasPermissoes);
    }

}
