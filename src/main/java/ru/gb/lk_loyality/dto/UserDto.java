package ru.gb.lk_loyality.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.lk_loyality.entities.User;

import java.util.Calendar;

@Data
@NoArgsConstructor
public class UserDto {
    private String email;
    private Integer cardNumber;
    private String cardQrCode;
    private String name;
    private String phone;
    private String sex;
    private Calendar birthday;
    private String city;
    private String status;

    public UserDto(User user) {
        this.email = user.getEmail();
        this.cardNumber = user.getCard().getCardNumber();
        this.cardQrCode = user.getCard().getQrCode();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.sex = user.getSex();
        this.birthday = user.getBirthday();
        this.city = user.getCity().getTitle();
        this.status = user.getStatus().getTitle();
    }


}
