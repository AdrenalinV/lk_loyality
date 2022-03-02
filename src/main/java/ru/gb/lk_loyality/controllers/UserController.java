package ru.gb.lk_loyality.controllers;

import lombok.RequiredArgsConstructor;


import org.springframework.web.bind.annotation.*;
import ru.gb.lk_loyality.dto.UserDto;
import ru.gb.lk_loyality.exceptions.ResourceNotFoundException;
import ru.gb.lk_loyality.services.UserService;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Получение ДТО пользователя по ID
     * @param id пользователя
     * @return ДТО пользователя
     */
    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        return userService.getUserDtoById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Пользователь с id = %d не найден", id)));
    }

}
