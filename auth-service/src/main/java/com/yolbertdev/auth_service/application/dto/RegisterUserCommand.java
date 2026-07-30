package com.yolbertdev.auth_service.application.dto;

import com.yolbertdev.auth_service.domain.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "Payload to register a new user account")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserCommand {

    @Schema(description = "User's first name — letters and spaces only", example = "John")
    @NotBlank
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+$", message = "Name must contain only letters and spaces")
    private String name;

    @Schema(description = "User's last name — letters and spaces only", example = "Doe")
    @NotBlank
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+$", message = "Lastname must contain only letters and spaces")
    private String lastname;

    @Schema(description = "Email address used as the login identifier", example = "john.doe@example.com")
    @NotBlank
    @Email
    private String email;

    @Schema(description = "Password — 8 to 30 characters", example = "Secur3P@ss!", minLength = 8, maxLength = 30)
    @NotBlank
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters")
    private String password;

}
