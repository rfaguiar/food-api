package com.food.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

//veja RFC 7807
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Problem(@JsonProperty("status") Integer status,
                      @JsonProperty("type") String type,
                      @JsonProperty("title") String title,
                      @JsonProperty("detail") String detail) {
}
