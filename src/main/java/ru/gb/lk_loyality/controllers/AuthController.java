package ru.gb.lk_loyality.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gb.lk_loyality.dto.AuthRequestDto;
import ru.gb.lk_loyality.dto.AuthResponseDto;
import ru.gb.lk_loyality.dto.SignUpRequestDto;
import ru.gb.lk_loyality.entities.User;
import ru.gb.lk_loyality.security.UserInfo;
import ru.gb.lk_loyality.security.repositories.JwtTokenService;
import ru.gb.lk_loyality.services.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService service;
    private final JwtTokenService tokenService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody SignUpRequestDto requestDto) {
        if(requestDto.getPassword().equals(requestDto.getPasPassword())){
            User user = new User();
            user.setPassword(requestDto.getPassword());
            user.setName(requestDto.getUserName());
            user.setEmail(requestDto.getEmail());
            service.saveUser(user);
        }
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequestDto request) {
        User user = service.findByNameAndPassword(request.getUserName(), request.getPassword());
        List<String> roles = new ArrayList<>();
        user.getRoles().forEach(role -> roles.add(role.getName()));
        UserInfo userInfo = UserInfo.builder()
                .userName(user.getName())
                .id(user.getId())
                .card_id(user.getCard().getId())
                .roles(roles).build();
        String token = tokenService.generateToken(userInfo);
        return new AuthResponseDto(token);
    }

}
