package com.food.api.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckSecurity {

    @interface Cozinhas {
        @PreAuthorize("isAuthenticated()")
        @Target({ElementType.METHOD})
        @Retention(RetentionPolicy.RUNTIME)
        @interface PodeConsultarCozinhas {}

        @PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
        @Target({ElementType.METHOD})
        @Retention(RetentionPolicy.RUNTIME)
        @interface PodeEditarCozinhas {}
    }
}
