package com.junioroffer.domain.loginandregister;

import lombok.Builder;

@Builder
public record User(Long id,
                   String userName,
                   String password) {
}
