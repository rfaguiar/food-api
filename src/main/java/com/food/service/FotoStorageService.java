package com.food.service;

import com.food.api.model.request.FotoProdutoRequest;

public interface FotoStorageService {

    void armazenar(FotoProdutoRequest fotoProdutoRequest);
}
