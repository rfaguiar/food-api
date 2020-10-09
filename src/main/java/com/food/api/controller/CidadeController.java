package com.food.api.controller;

import com.food.api.helper.ResourceUriHelper;
import com.food.api.model.request.CidadeRequest;
import com.food.api.model.response.CidadeResponse;
import com.food.api.openapi.controller.CidadeControllerOpenApi;
import com.food.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController implements CidadeControllerOpenApi {

    private final CidadeService cidadeService;

    @Autowired
    public CidadeController(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }

    @GetMapping
    public List<CidadeResponse> listar() {
        return cidadeService.todos();
    }

    @GetMapping("/{cidadeId}")
    public CidadeResponse porId(@PathVariable Long cidadeId) {
        CidadeResponse cidadeResponse = cidadeService.buscarPorId(cidadeId);
        var linkCidadeController = linkTo(CidadeController.class);
        var linkId = linkCidadeController.slash(cidadeResponse.getId())
                .withSelfRel();
        cidadeResponse.add(linkId);
        Link linkCidades = linkCidadeController.withRel("cidades");
        cidadeResponse.add(linkCidades);
        var linkEstados = linkTo(EstadoController.class)
                .slash(cidadeResponse.getEstado().getId())
                .withSelfRel();
        cidadeResponse.getEstado().add(linkEstados);
        return cidadeResponse;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeResponse adicionar(@RequestBody @Valid CidadeRequest cidade) {
        CidadeResponse cidadeResponse = cidadeService.adicionar(cidade);
        ResourceUriHelper.addUriInResponseHeader(cidadeResponse.getId());
        return cidadeResponse;
    }

    @PutMapping("/{cidadeId}")
    public CidadeResponse atualizar(@PathVariable Long cidadeId,
                                    @RequestBody @Valid CidadeRequest cidade) {
        return cidadeService.atualizar(cidadeId, cidade);
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cidadeService.remover(cidadeId);
    }
}
