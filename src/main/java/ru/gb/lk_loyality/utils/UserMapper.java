package ru.gb.lk_loyality.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.lk_loyality.dto.CounterResponseDto;
import ru.gb.lk_loyality.dto.DocumentResponseDto;
import ru.gb.lk_loyality.dto.UserDto;
import ru.gb.lk_loyality.entities.Counter;
import ru.gb.lk_loyality.entities.Document;
import ru.gb.lk_loyality.entities.User;
import ru.gb.lk_loyality.services.CardService;
import ru.gb.lk_loyality.services.CityService;
import ru.gb.lk_loyality.services.DocumentService;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final CityService cityService;
    private final CardService cardService;

    public UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setCardNumber(user.getCard().getCardNumber());
        userDto.setCardQrCode(user.getCard().getQrCode());
        userDto.setName(user.getName());
        userDto.setPhone(user.getPhone());
        userDto.setSex(user.getSex());
        userDto.setBirthday(user.getBirthday());
        userDto.setCity(user.getCity().getTitle());
        User.Status status = User.Status.values()[user.getStatus()];
        userDto.setStatus(status.toString());
        return userDto;
    }

    public User mapToUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setCard(cardService.getCardByNumber(userDto.getCardNumber()).get());
        user.setName(userDto.getName());
        user.setPhone(userDto.getPhone());
        user.setSex(userDto.getSex());
        user.setBirthday(userDto.getBirthday());
        user.setCity(cityService.getCityByTitle(userDto.getCity()));
        User.Status status = User.Status.valueOf(userDto.getStatus());
        user.setStatus(status.ordinal());
        return user;
    }
}
