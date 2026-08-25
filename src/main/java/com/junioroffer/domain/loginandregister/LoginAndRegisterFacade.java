package com.junioroffer.domain.loginandregister;

import com.junioroffer.domain.loginandregister.dto.RegisterUserDto;
import com.junioroffer.domain.loginandregister.dto.RegistrationResultDto;
import com.junioroffer.domain.loginandregister.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class LoginAndRegisterFacade {

    private static final String USER_NOT_FOUND = "User not found";

    private final LoginRepository loginRepository;

    public UserDto findUserByUserName(String userName){
        return loginRepository.findByUserName(userName)
                .map(UserMapper::mapUserToUserDto)
                .orElseThrow(() -> new BadCredentialsException(USER_NOT_FOUND));
    }
    public RegistrationResultDto register(RegisterUserDto registerUserDto){
        User user = UserMapper.mapUserDtoToUser(registerUserDto);
        User addedUser = loginRepository.save(user);
        return new RegistrationResultDto(addedUser.id(), true, addedUser.userName());
    }
}
