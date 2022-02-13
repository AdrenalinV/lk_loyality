package ru.gb.lk_loyality.utils;

import org.springframework.stereotype.Service;
import ru.gb.lk_loyality.dto.UserDto;
import ru.gb.lk_loyality.entities.User;
import ru.gb.lk_loyality.services.CardService;
import ru.gb.lk_loyality.services.CityService;
import ru.gb.lk_loyality.services.StatusService;

@Service
public class MappingUtils {

    private CityService cityService;
    private StatusService statusService;
    private CardService cardService;


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
        userDto.setStatus(user.getStatus().getTitle());
        return userDto;
    }

    public User mapToUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setCard(cardService.getCardByNumber(userDto.getCardNumber()));
        user.setName(userDto.getName());
        user.setPhone(userDto.getPhone());
        user.setSex(userDto.getSex());
        user.setBirthday(userDto.getBirthday());
        user.setCity(cityService.getCityByTitle(userDto.getCity()));
        user.setStatus(statusService.getStatusByTitle(userDto.getStatus()));
        return user;
    }
}
