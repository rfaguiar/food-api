package com.food.api.model.response;

import java.io.InputStream;

public record FotoStreamResponse(String contentType,
                                 InputStream fotoArquivo) {
}
