package com.junioroffer.domain.loginandregister;

import com.junioroffer.domain.loginandregister.dto.RegisterUserDto;
import com.junioroffer.domain.loginandregister.dto.RegistrationResultDto;
import com.junioroffer.domain.loginandregister.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.assertThat;


class LoginAndRegisterFacadeTest {

    LoginRepositoryTestImpl userRepository = new LoginRepositoryTestImpl();
    LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterFacade(userRepository);

    @Test
    public void should_throw_exceptions_when_user_not_found() {
        //given
        String userName = "Antek";
        //when
        Throwable throwable = catchThrowable(() -> loginAndRegisterFacade.findUserByUserName(userName));
        //then
        assertThat(throwable).isInstanceOf(BadCredentialsException.class);
        assertThat(throwable.getMessage()).isEqualTo("User not found");
    }

    @Test
    public void should_find_user_by_user_name() {
        //given
        RegisterUserDto user = new RegisterUserDto("Antek", "12345");
        RegistrationResultDto register = loginAndRegisterFacade.register(user);
        //when
        UserDto result = loginAndRegisterFacade.findUserByUserName(register.userName());
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
        RegistrationResultDto result = loginAndRegisterFacade.register(user);
        //then
        assertThat(result.created()).isTrue();
        assertThat(result.userName()).isEqualTo("Antek");


    }
}