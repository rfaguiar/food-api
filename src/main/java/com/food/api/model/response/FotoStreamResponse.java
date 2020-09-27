package com.food.api.model.response;

import com.food.service.FotoStorageService;

public record FotoStreamResponse(String contentType,
                                 FotoStorageService.FotoRecuperada fotoArquivo) {
}
