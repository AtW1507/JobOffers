package com.junioroffer.domain.login;

import com.junioroffer.domain.login.dto.RegisterUserDto;
import com.junioroffer.domain.login.dto.RegistrationUserDto;
import com.junioroffer.domain.login.dto.UserDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.assertThat;


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
        RegisterUserDto user = new RegisterUserDto("Antek", "12345");
        RegistrationUserDto register = loginAndRegisterFacade.register(user);
        //when
        UserDto result = loginAndRegisterFacade.findUserByUserName(register.username());
        //then
        assertThat(result)
                .isNotNull()
                .extracting(UserDto::userName)
                .isEqualTo("Antek");
        assertThat(result.id()).isEqualTo(register.id());
    }

    @Test
    public void should_register_user() {
        //given
        RegisterUserDto user = RegisterUserDto.builder()
                .userName("Antek")
                .password("12345")
                .build();
        //when
        RegistrationUserDto result = loginAndRegisterFacade.register(user);
        //then
        assertThat(result.created()).isTrue();
        assertThat(result.username()).isEqualTo("Antek");


    }
}