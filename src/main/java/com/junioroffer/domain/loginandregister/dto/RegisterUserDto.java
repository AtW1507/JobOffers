package com.junioroffer.domain.loginandregister.dto;

import lombok.Builder;

@Builder
public record RegisterUserDto(String userName, String password) {
}
