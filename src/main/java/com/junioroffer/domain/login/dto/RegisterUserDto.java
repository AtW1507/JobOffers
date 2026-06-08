package com.junioroffer.domain.login.dto;

import lombok.Builder;

@Builder
public record RegisterUserDto(String userName, String password) {
}
