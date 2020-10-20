package com.food.api.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface CheckSecurity {

    @interface Cozinhas {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
        @Target({METHOD})
        @Retention(RUNTIME)
        @interface PodeEditarCozinhas {}

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Target({METHOD})
        @Retention(RUNTIME)
        @interface PodeConsultarCozinhas {}
    }

    @interface Restaurantes {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeEditar { }

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar { }

    }
}
