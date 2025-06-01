package com.example.Quick.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
        title = "Quick Commerce API",
        version = "1.0.0",
        description = "RESTful API for Quick Commerce e-commerce platform",
        contact = @Contact(
            name = "API Support",
            email = "support@quickcommerce.example.com"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0.html"
        )
    ),
    servers = {
        @Server(url = "http://localhost:8080", description = "Local Development Server")
    }
)
public class OpenApiConfig {
    // No beans required - using annotation-based configuration
}
