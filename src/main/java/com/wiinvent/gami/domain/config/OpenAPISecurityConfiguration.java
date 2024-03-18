package com.wiinvent.gami.domain.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info =@Info(
        title = "Lucky present test",
        version = "v1",
        contact = @Contact(
            name = "lambro2510", email = "lambro2510@gmail.com", url = "https://cms.luckypresent.com.vn"
        ),
        license = @License(
            name = "Nginx", url = ""
        ),
        description = "api mô tả"
    ),
    servers = {
        @Server(url = "https://api.luckypresent.com.vn", description = "Production Server"),
        @Server(url = "http://localhost:8080", description = "Local Server")
    }

)
public class OpenAPISecurityConfiguration {
  @Bean
  public OpenAPI customizeOpenAPI() {
    final String securitySchemeName = "bearerAuth";
    return new OpenAPI()
        .addSecurityItem(new SecurityRequirement()
            .addList(securitySchemeName))
        .components(new Components()
            .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                .name(securitySchemeName)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")));
  }
}