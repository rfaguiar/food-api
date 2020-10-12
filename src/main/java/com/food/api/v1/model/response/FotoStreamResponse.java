package com.food.api.v1.model.response;

import com.food.service.FotoStorageService;

public record FotoStreamResponse(String contentType,
                                 FotoStorageService.FotoRecuperada fotoArquivo) {
}
