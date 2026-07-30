package com.yolbertdev.auth_service.application.dto;

import com.yolbertdev.auth_service.domain.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "Payload to change the user role")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoleCommand {

    @Schema(description = "New user role", example = "ADMIN")
    @NotNull
    private UserRole role;

}
