package com.furkanyesilyurt.couriertracking.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties("swagger")
@PropertySource("classpath:swagger.properties")
public class SwaggerConfig {

    @Value("${swagger.contact.email}")
    private String email;

    @Value("${swagger.contact.name}")
    private String name;

    @Value("${swagger.info.title}")
    private String title;

    @Value("${swagger.info.description}")
    private String description;

    @Value("${swagger.info.version}")
    private String version;

    @Bean
    public OpenAPI customOpenAPI() {

        Contact contact = new Contact()
                .email(email)
                .name(name);

        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .description(description)
                        .version(version)
                        .contact(contact)
                );
    }
}
