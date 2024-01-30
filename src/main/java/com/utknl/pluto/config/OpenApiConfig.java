package com.utknl.pluto.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Pluto Reporting API",
                version = "1.0",
                description = "Pluto Reporting API"
        ),
        servers = {
                @io.swagger.v3.oas.annotations.servers.Server(
                        description = "Sandbox Server",
                        url = "http://localhost:8080"
                )
        }
)

@SecurityScheme(type = SecuritySchemeType.APIKEY, name = "Authorization", in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {
}
