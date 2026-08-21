package uz.urspi.newurspi.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        final String securitySchemeName = "Bearer Authentication";

        return new OpenAPI()
                // Relative URL: Swagger uses current page origin (http/https) — avoids Mixed Content NetworkError
                .servers(List.of(new Server().url("/").description("Current host")))
                .info(new Info()
                        .title("URSPI REST API")
                        .version("v1.0.0")
                        .description("Urganch State Pedagogical Institute backend REST API")
                        .contact(new Contact()
                                .name("Akbar Otaboev")
                                .email("otaboevakbar96@gmail.com"))
                        .license(new License()
                                .name("MIT License")))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .schemaRequirement(
                        securitySchemeName,
                        new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                );
    }
}
