package com.food.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CidadeIdRequest(@JsonProperty("id") Long id) {
}
