package com.junioroffer.domain.loginandregister;

import lombok.Builder;

@Builder
public record User(String id,
                   String userName,
                   String password) {
}
