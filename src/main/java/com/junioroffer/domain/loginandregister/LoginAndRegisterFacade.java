package com.junioroffer.domain.loginandregister;

import com.junioroffer.domain.loginandregister.dto.UserDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class LoginAndRegisterFacade {

    UserRepository userRepository;

    public UserDto findUserByUserName(String userName){
        return userRepository.findByUserName(userName)
                .map(UserMapper::mapUserToUserDto)
                .orElseThrow(() -> new UserNotFoundException("User with username: " + userName + " not found"));
    }
    public UserDto register(User user){
        User addedUser = userRepository.addUser(user);
        return UserDto.builder()
                .userName(addedUser.userName())
                .password(addedUser.password())
                .build();
    }
}
