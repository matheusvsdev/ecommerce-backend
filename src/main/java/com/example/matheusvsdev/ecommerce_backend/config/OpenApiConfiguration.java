package com.example.matheusvsdev.ecommerce_backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("E-commerce-backend API")
                        .version("1.0")
                        .description("Documentation of E-commerce-backend API")
                        .termsOfService("https://example.com/terms")
                        .contact(new Contact()
                                .name("Matheus Valdevino")
                                .email("matheusvaldevino1997@gmail.com")
                                .url("https://yourwebsite.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}
