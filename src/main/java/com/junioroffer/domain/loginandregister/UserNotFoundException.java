package com.junioroffer.domain.loginandregister;

class UserNotFoundException extends RuntimeException {
    public UserNotFoundException( String message ) {
        super(message);
    }
}
