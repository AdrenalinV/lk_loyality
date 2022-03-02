package ru.gb.lk_loyality.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.gb.lk_loyality.dto.AuthRequestDto;
import ru.gb.lk_loyality.dto.AuthResponseDto;
import ru.gb.lk_loyality.entities.Role;
import ru.gb.lk_loyality.entities.User;
import ru.gb.lk_loyality.security.UserInfo;
import ru.gb.lk_loyality.security.repositories.JwtTokenService;
import ru.gb.lk_loyality.services.UserService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class AuthControllerTest {
    private UserService service = Mockito.mock(UserService.class);
    private JwtTokenService tokenService = Mockito.mock(JwtTokenService.class);
    private AuthController controller = new AuthController(service, tokenService);

    @Test
    @DisplayName("")
    void loginTest() {
        AuthRequestDto request = new AuthRequestDto("Vasya", "123456");
        Mockito.when(service.findByNameAndPassword("Vasya", "123456")).thenReturn(generateUser());
        Mockito.when(tokenService.generateToken(any())).thenReturn("TEST Token string");


        AuthResponseDto response = controller.login(request);

        Assertions.assertEquals(response.getToken(), "TEST Token string");
        Mockito.verify(service).findByNameAndPassword(eq("Vasya"), eq("123456"));
        Mockito.verify(tokenService).generateToken(any(UserInfo.class));

    }

    private User generateUser() {
        Role role = new Role();
        role.setName("ROLE_TEST");
        role.setId(0L);

        User user = new User();
        user.setId(1L);
        user.setName("Vasya");
        user.setRoles(Collections.singletonList(role));
        return user;
    }
}
