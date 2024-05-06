package com.newcord.userservice.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Newcord User Service API",
                version = "1.0",
                description = "Documentation Newcord User Service API v1.0"
        )
)

public class SwaggerConfig {

}