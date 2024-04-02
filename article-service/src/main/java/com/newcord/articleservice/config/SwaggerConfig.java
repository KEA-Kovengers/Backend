package com.newcord.articleservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Newcord Article Service API",
                version = "1.0",
                description = "Documentation Newcord Article Service API v1.0"
        )
)
@Configuration
public class SwaggerConfig {

}
