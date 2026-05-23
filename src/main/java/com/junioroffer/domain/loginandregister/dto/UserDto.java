package com.junioroffer.domain.loginandregister.dto;

import lombok.Builder;

@Builder
public record UserDto(String id,
                      String userName,
                      String password) {
}
