package com.food.config;

import org.springframework.http.MediaType;

public final class FoodMediaTypes {

    private FoodMediaTypes() {}

    public static final String V1_APPLICATION_JSON_VALUE = "application/vnd.food.v1+json";
    public static final MediaType V1_APPLICATION_JSON = MediaType.valueOf(V1_APPLICATION_JSON_VALUE);
}
