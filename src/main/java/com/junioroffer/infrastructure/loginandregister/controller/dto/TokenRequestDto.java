package com.junioroffer.infrastructure.loginandregister.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record TokenRequestDto(
        @NotBlank(message = "{username.not.blank}")
        String userName,

        @NotBlank(message = "{password.not.blank}")
        String password
) {
}
