package com.food.service;

import java.io.InputStream;

public interface FotoStorageService {

    void armazenar(NovaFoto novaFoto);
    void remover(String nomeArquivo);
    FotoRecuperada recuperar(String nomeArquivo);

    record NovaFoto(String nomeArquivo, String contentType, InputStream fotoInputStream) {}

    record FotoRecuperada(InputStream inputStream, String url){
        public boolean temUrl() {
            return url != null;
        }

        public boolean temInputStream() {
            return inputStream != null;
        }
    }
}
