package com.food.jpa;

import com.food.Application;
import com.food.domain.model.Cozinha;
import com.food.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class CadastroCozinhaMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CozinhaRepository cozinhas = applicationContext.getBean(CozinhaRepository.class);
        Cozinha cozinhaBr = new Cozinha(null, "Brasileira");
        System.out.println(cozinhas.adicionar(cozinhaBr));

        Cozinha cozinhaJap = new Cozinha(null, "Japoneza");
        System.out.println(cozinhas.adicionar(cozinhaJap));
    }
}
