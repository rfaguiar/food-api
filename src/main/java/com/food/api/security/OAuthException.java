package com.food.api.security;

public class OAuthException extends RuntimeException {

    public OAuthException(String message) {
        super(message, null,true,false);
    }
}