package com.junioroffer.domain.login;

class UserNotFoundException extends RuntimeException {
    public UserNotFoundException( String message ) {
        super(message);
    }
}
