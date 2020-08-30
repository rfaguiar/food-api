package com.food.jpa;

import com.food.Application;
import com.food.domain.repository.PermissaoRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class ConsultaPermissaoMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.NONE)
                .run(args);

        PermissaoRepository permissoes = applicationContext.getBean(PermissaoRepository.class);
        permissoes.todas()
            .forEach(System.out::println);
    }
}
