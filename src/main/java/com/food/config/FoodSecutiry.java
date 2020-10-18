package com.food.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class FoodSecutiry {

    public Long getUsuarioId() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();
        return jwt.getClaim("usuario_id");
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext()
                .getAuthentication();
    }
}
