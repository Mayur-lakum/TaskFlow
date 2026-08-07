package com.mayur.taskflowai.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI taskFlowOpenAPI() {

        return new OpenAPI()

                .info(new Info()

                        .title("TaskFlow AI API")

                        .description("""
                                Enterprise Employee Recommendation System

                                Features
                                • Employee Management
                                • Project Management
                                • Skill Management
                                • JWT Authentication
                                • Rule-Based Recommendation Engine
                                """)

                        .version("1.0.0")

                        .contact(new Contact()
                                .name("Mayur Lakum")
                                .email("mayur@example.com")
                                .url("https://github.com/Mayur-lakum"))

                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))

                .externalDocs(
                        new ExternalDocumentation()
                                .description("GitHub Repository")
                                .url("https://github.com/Mayur-lakum"));
    }
}