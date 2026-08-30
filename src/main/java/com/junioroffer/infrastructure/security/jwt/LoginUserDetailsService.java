package com.junioroffer.infrastructure.security.jwt;

import com.junioroffer.domain.loginandregister.LoginAndRegisterFacade;
import com.junioroffer.domain.loginandregister.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.Collections;

@AllArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {

    private final LoginAndRegisterFacade loginAndRegisterFacade;

    @Override
    public UserDetails loadUserByUsername(String username) throws BadCredentialsException {
        UserDto userDto = loginAndRegisterFacade.findUserByUserName(username);
        return getUser(userDto);
    }

    private org.springframework.security.core.userdetails.User getUser(UserDto user){
        return new org.springframework.security.core.userdetails.User(
                user.userName(),
                user.password(),
                Collections.emptyList());
    }
}
