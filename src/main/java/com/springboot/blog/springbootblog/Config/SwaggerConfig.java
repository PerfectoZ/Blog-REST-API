package com.springboot.blog.springbootblog.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import static io.swagger.v3.oas.models.security.SecurityScheme.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;

//Migrating from springfox to springdoc
//https://springdoc.org/migrating-from-springfox.html

//http://localhost:8080/swagger-ui/index.html
//localhost:8080/v3/api-docs

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info().title("Spring Boot Blog REST APIs")
                        .description("Spring Boot Blog REST API Documentation")
                        .version("v1.0")
                        .license(new License().name("API License").url("https://github.com/"))
                        .contact(new Contact().name("PerfectoZ").url("https://github.com/PerfectoZ").email("mandeepsinghtaneja_it20b10_47@dtu.ac.in/")))
                .externalDocs(new ExternalDocumentation()
                        .description("Spring Boot Blog App Documentation")
                        .url("https://github.com/docs"))
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .addSecurityItem(
                        new SecurityRequirement().addList("bearer-jwt", Arrays.asList("read", "write"))
                                .addList("bearer-key", Collections.emptyList())
                );
    }

}
