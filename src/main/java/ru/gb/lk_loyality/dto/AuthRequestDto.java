package ru.gb.lk_loyality.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequestDto {
    private String userName;
    private String password;
}
