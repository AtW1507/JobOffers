package com.junioroffer.domain.login;

import lombok.Builder;

@Builder
public record User(String id,
                   String userName,
                   String password) {
}
