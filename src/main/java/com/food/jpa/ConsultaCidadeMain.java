package com.food.jpa;

import com.food.Application;
import com.food.domain.repository.CidadeRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class ConsultaCidadeMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CidadeRepository cidades = applicationContext.getBean(CidadeRepository.class);
        cidades.todas()
            .forEach(System.out::println);
    }
}
