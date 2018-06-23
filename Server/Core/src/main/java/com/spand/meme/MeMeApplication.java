package com.spand.meme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@PropertySource("classpath:application-prod.properties")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MeMeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeMeApplication.class, args);
    }

}