package ru.gb.lk_loyality.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserDto {
    private String email;
    private Integer cardNumber;
    private String cardQrCode;
    private String name;
    private String phone;
    private String sex;
    private LocalDate birthday;
    private String city;
    private String status;

}
