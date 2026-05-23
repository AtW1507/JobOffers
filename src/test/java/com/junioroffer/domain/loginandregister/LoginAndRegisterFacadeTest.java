package com.junioroffer.domain.loginandregister;

import com.junioroffer.domain.loginandregister.dto.UserDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LoginAndRegisterFacadeTest {

    UserRepositoryTestImpl userRepository = new UserRepositoryTestImpl();
    LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterFacade(userRepository);

    @Test
    public void should_throw_exceptions_when_user_not_found() {
        //given
        String userName = "Antek";
        //when
        Throwable throwable = catchThrowable(() -> loginAndRegisterFacade.findUserByUserName(userName));
        //then
        assertThat(throwable).isInstanceOf(UserNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("User with username: " + userName + " not found");
    }

    @Test
    public void should_find_user_by_user_name() {
        //given
        User user = User.builder()
                .userName("Antek")
                .password("12345")
                .build();
        userRepository.addUser(user);
        //when
        UserDto result = loginAndRegisterFacade.findUserByUserName(user.userName());
        //then
        assertThat(result)
                .isNotNull()
                .extracting(UserDto::userName)
                .isEqualTo("Antek");
    }

    @Test
    public void should_register_user() {
        //given
        User user = User.builder()
                .userName("Antek")
                .password("12345")
                .build();
        //when
        UserDto result = loginAndRegisterFacade.register(user);
        //then
        assertThat(result)
                .isNotNull()
                .extracting(UserDto::userName)
                .isEqualTo("Antek");


    }
}