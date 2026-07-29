package com.yolbertdev.auth_service.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Global OpenAPI 3 configuration.
 *
 * <p>Defines the API metadata (title, version, description, contact), the JWT Bearer security
 * scheme used across all protected endpoints, and the logical tag groups that organise the Swagger
 * UI.
 */
@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI openApi() {
    return new OpenAPI()
        .info(apiInfo())
        .servers(
            List.of(new Server().url("http://localhost:8080").description("Local development")))
        .components(new Components().addSecuritySchemes("BearerAuth", jwtSecurityScheme()))
        .tags(
            List.of(
                new Tag()
                    .name("Authentication")
                    .description("User registration, login, token refresh and logout"),
                new Tag()
                    .name("OTP")
                    .description("One-time password validation, resend and password reset flows"),
                new Tag().name("Sessions").description("List and revoke active device sessions")));
  }

  // ── Private helpers ──────────────────────────────────────────────────────

  private Info apiInfo() {
    return new Info()
        .title("Cinema Auth Service API")
        .description(
            """
            Authentication and authorization microservice for the **Cinema** platform.

            ## Features
            - User registration and login with brute-force protection
            - JWT access tokens (15 min) + refresh tokens (7 days)
            - Multi-device session management
            - Email verification via 6-digit OTP
            - Password reset via OTP
            - 2FA support via one-time codes
            - Domain events published through the Transactional Outbox pattern

            ## Security
            Protected endpoints require a **Bearer JWT** access token in the
            `Authorization` header.  Obtain one by calling `POST /api/auth/login`.
            """)
        .version("1.0.0")
        .contact(
            new Contact()
                .name("Yolbert Torrealba")
                .email("yolbert@yolbertdev.com")
                .url("https://github.com/yolbert28"))
        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT"));
  }

  private SecurityScheme jwtSecurityScheme() {
    return new SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT")
        .description(
            "Paste the **access token** returned by `POST /api/auth/login` or `POST"
                + " /api/auth/refresh`.");
  }
}
