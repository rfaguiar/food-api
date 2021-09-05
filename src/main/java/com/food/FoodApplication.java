package com.food;

import com.food.config.io.Base64ProtocolResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class FoodApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        var application = new SpringApplication(FoodApplication.class);
        application.addListeners(new Base64ProtocolResolver());
        application.run(args);
    }

}
