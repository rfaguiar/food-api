package com.food.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ApiDeprecationHandler apiDeprecationHandler;
    private final ApiRetirementHandler apiRetirementHandler;

    @Autowired
    public WebConfig(ApiDeprecationHandler apiDeprecationHandler, ApiRetirementHandler apiRetirementHandler) {
        this.apiDeprecationHandler = apiDeprecationHandler;
        this.apiRetirementHandler = apiRetirementHandler;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiDeprecationHandler);
        registry.addInterceptor(apiRetirementHandler);
    }
}
