package com.junioroffer.domain.loginandregister;

import com.junioroffer.domain.loginandregister.dto.RegisterUserDto;
import com.junioroffer.domain.loginandregister.dto.RegistrationUserDto;
import com.junioroffer.domain.loginandregister.dto.UserDto;
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
