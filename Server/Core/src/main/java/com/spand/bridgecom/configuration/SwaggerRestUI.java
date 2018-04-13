package com.spand.bridgecom.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger configuration for client api
 * Address to check locally: http://localhost:8080/api/v2/api-docs
 **/
@EnableSwagger2
@Configuration
@ComponentScan(basePackages = "com.spand.bridgecom")
public class SwaggerRestUI {

    @Value("${swagger.service.basepackage}")
    private String basePackage;

    /**
     * Docket bean configuration.
     *
     * @return - returns a bean, that is user by Swagger to scan a base package, in order to find
     * endpoints to be documented. Then it generates client code and Swagger-UI based on a
     * collected information.
     */
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .build();
    }

}
