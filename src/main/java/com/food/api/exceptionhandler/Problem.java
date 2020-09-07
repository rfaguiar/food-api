package com.food.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

//veja RFC 7807
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Problem(@JsonProperty("status") Integer status,
                      @JsonProperty("type") String type,
                      @JsonProperty("title") String title,
                      @JsonProperty("detail") String detail,
                      @JsonProperty("userMessage") String userMessage,
                      @JsonProperty("timestamp") LocalDateTime timestamp,
                      @JsonProperty("fields") List<Field> fields) {
    public Problem(Integer status,
                   String type,
                   String title,
                   String detail,
                   String userMessage) {
        this(status, type, title, detail, userMessage, LocalDateTime.now(), null);
    }
}
