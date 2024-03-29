package ru.gb.lk_loyality.controllers;

import lombok.RequiredArgsConstructor;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.gb.lk_loyality.dto.UserDto;
import ru.gb.lk_loyality.exceptions.ResourceNotFoundException;
import ru.gb.lk_loyality.security.UserInfo;
import ru.gb.lk_loyality.services.UserService;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * авторизованные пользователи
     * получение пользователя по токену
     * @return ДТО пользователя
     */
    @GetMapping("/my")
    public UserDto getUser() {
        UserInfo user = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.getUserDtoById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Пользователь с id = %d не найден", user.getId())));
    }

    /**
     * только роль ROLE_ADMIN
     * получение пользователя по id
     *
     * @param id id пользователя
     * @return ДТО пользователя
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable(name="id") Long id) {
        return userService.getUserDtoById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Пользователь с id = %d не найден", id)));
    }



}
