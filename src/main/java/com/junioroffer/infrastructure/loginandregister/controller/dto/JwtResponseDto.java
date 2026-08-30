package com.junioroffer.infrastructure.loginandregister.controller.dto;

import lombok.Builder;

@Builder
public record JwtResponseDto(
        String userName,
        String token
) {
}
