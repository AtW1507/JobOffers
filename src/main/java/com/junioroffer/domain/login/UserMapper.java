package com.junioroffer.domain.login;

import com.junioroffer.domain.login.dto.RegisterUserDto;
import com.junioroffer.domain.login.dto.UserDto;

class UserMapper {

    static UserDto mapUserToUserDto(User user){
        return UserDto.builder()
                .id(user.id())
                .userName(user.userName())
                .password(user.password())
                .build();
    }
    static User mapUserDtoToUser(RegisterUserDto userDto){
        return User.builder()
                .userName(userDto.userName())
                .password(userDto.password())
                .build();
    }
}
