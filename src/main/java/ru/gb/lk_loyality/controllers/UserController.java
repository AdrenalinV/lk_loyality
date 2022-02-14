package ru.gb.lk_loyality.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.lk_loyality.dto.UserDto;
import ru.gb.lk_loyality.entities.Counter;
import ru.gb.lk_loyality.exceptions.ResourceNotFoundException;
import ru.gb.lk_loyality.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{id}")
    public UserDto getUser(@PathVariable Long id) {
        System.out.println("test");
        return userService.getUserDtoById(id).orElseThrow(() -> new ResourceNotFoundException("Пользователь с id = " + id + " не найден"));
    }

    @GetMapping("/counters/{id}")
    public Float getBalance(@PathVariable Long id) {
        return userService.getBalanceByIdUser(id);
    }
}
