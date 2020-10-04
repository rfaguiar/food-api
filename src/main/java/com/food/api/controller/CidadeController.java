package com.food.api.controller;

import com.food.api.model.request.CidadeRequest;
import com.food.api.model.response.CidadeResponse;
import com.food.config.OpenApiConfig;
import com.food.service.CidadeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

@Api(tags = OpenApiConfig.TAG_CIDADE)
@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    private final CidadeService cidadeService;

    @Autowired
    public CidadeController(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }

    @ApiOperation("Lista as cidades")
    @GetMapping
    public List<CidadeResponse> listar() {
        return cidadeService.todos();
    }

    @ApiOperation("Busca uma cidade por ID")
    @GetMapping("/{cidadeId}")
    public CidadeResponse porId(@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long cidadeId) {
        return cidadeService.buscarPorId(cidadeId);
    }

    @ApiOperation("Cadastra uma cidade")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeResponse adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade")
                                    @RequestBody @Valid CidadeRequest cidade) {
        return cidadeService.adicionar(cidade);
    }

    @ApiOperation("Atualiza uma cidade por ID")
    @PutMapping("/{cidadeId}")
    public CidadeResponse atualizar(@ApiParam(value = "ID de uma cidade", example = "1")
                                    @PathVariable Long cidadeId,
                                    @ApiParam(name = "corpo", value = "Representação de uma nova cidade com os novos dados")
                                    @RequestBody @Valid CidadeRequest cidade) {
        return cidadeService.atualizar(cidadeId, cidade);
    }

    @ApiOperation("Exclui uma cidade por ID")
    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@ApiParam(value = "ID de uma cidade", example = "1")
                        @PathVariable Long cidadeId) {
        cidadeService.remover(cidadeId);
    }
}
