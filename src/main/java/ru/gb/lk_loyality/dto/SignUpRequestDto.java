package ru.gb.lk_loyality.dto;

import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class SignUpRequestDto {
    private String userName;
    private String password;
    private String pasPassword;
    private String email;
}
