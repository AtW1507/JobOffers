package com.junioroffer.domain.login;

import com.junioroffer.domain.login.dto.RegisterUserDto;
import com.junioroffer.domain.login.dto.RegistrationUserDto;
import com.junioroffer.domain.login.dto.UserDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class LoginAndRegisterFacade {

    private final UserRepository userRepository;

    public UserDto findUserByUserName(String userName){
        return userRepository.findByUserName(userName)
                .map(UserMapper::mapUserToUserDto)
                .orElseThrow(() -> new UserNotFoundException("User with username: " + userName + " not found"));
    }
    public RegistrationUserDto register(RegisterUserDto registerUserDto){
        User user = UserMapper.mapUserDtoToUser(registerUserDto);
        User addedUser = userRepository.addUser(user);
        return new RegistrationUserDto(addedUser.id(), true, addedUser.userName());
    }
}
