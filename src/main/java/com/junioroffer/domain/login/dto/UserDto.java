package com.junioroffer.domain.login.dto;

import lombok.Builder;

@Builder
public record UserDto(String id,
                      String userName,
                      String password) {
}
