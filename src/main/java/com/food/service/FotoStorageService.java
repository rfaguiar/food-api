package com.food.service;

import java.io.InputStream;

public interface FotoStorageService {

    void armazenar(NovaFoto novaFoto);
    void remover(String nomeArquivo);
    InputStream recuperar(String nomeArquivo);

    record NovaFoto(String nomeArquivo, InputStream fotoInputStream) {}
}
