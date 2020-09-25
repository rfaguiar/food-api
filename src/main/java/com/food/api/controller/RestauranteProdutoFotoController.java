package com.food.api.controller;

import com.food.api.model.request.FotoProdutoRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/restaurante/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void atualizarFoto(@PathVariable Long restauranteId,
                              @PathVariable String produtoId,
                              @Valid FotoProdutoRequest fotoProdutoRequest) {
        var nomeArquivo = UUID.randomUUID().toString() + "_" + fotoProdutoRequest.getArquivo().getOriginalFilename();


    }
}
