package com.junioroffer.domain.loginandregister.dto;

import lombok.Builder;

@Builder
public record UserDto(Long id,
        String userName,
                      String password) {
}
