package com.food.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ApiDeprecationHandler extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURI().startsWith("/v1")) {
            response.addHeader("X-Food-Deprecated",
                    "Essa versão da API está depreciada e deixará de existir a partir de 01/01;2021. Use a versão mais atual da API.");
        }
        return super.preHandle(request, response, handler);
    }
}
