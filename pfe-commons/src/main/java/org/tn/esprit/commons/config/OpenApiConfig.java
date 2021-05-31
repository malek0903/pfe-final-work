package org.tn.esprit.commons.config;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

import javax.ws.rs.core.Application;


@SecurityScheme(
        securitySchemeName = "jwt",
        description = "JWT authentication with bearer token",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "Bearer [token]")
@OpenAPIDefinition(
        info = @Info(
                title = "Workflow ms API",
                description = " pfe application 'quarkus apps with docker and kubernetes'",
                contact = @Contact(name = "Malek ben yakhlef", email =
                "malekbenyakhlef1@gmail.com", url = "https://www.linkedin.com/in/malek-benyakhlef-8b685a187/"),
                version = "1.0.0-SNAPSHOT"
        ),
        security = @SecurityRequirement(name = "JWT")
)
public class OpenApiConfig extends Application {

}
