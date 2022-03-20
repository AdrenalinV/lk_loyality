package ru.gb.lk_loyality.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.gb.lk_loyality.dto.UserDto;
import ru.gb.lk_loyality.entities.Card;
import ru.gb.lk_loyality.entities.City;
import ru.gb.lk_loyality.entities.User;
import ru.gb.lk_loyality.services.CardService;
import ru.gb.lk_loyality.services.CityService;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class MappingUtilsTest {
    CityService cityService = Mockito.mock(CityService.class);
    CardService cardService = Mockito.mock(CardService.class);

    @DisplayName("Тест преобразования User в DTO object")
    @Test
    void userToUserDto() {

        UserMapper mappingUtils = new UserMapper(cityService, cardService);
        UserDto expected = mappingUtils.mapToUserDto(generateUser());
        UserDto actual = generateUserDto();
        Assertions.assertEquals(expected,actual);


    }

    @DisplayName("Тест преобразования UserDto в User object")
    @Test
    void userDtoToUser() {
        Mockito.when(cityService.getCityByTitle(any(String.class))).thenReturn(generateCity());
        Mockito.when(cardService.getCardByNumber(any())).thenReturn(Optional.of(generateCard()));

        UserMapper mappingUtils = new UserMapper(cityService, cardService);
        UserDto userDto = generateUserDto();
        User expected = mappingUtils.mapToUser(userDto);
        User actual = generateUser();
        Mockito.verify(cityService, Mockito.times(1)).getCityByTitle(eq(userDto.getCity()));
        Mockito.verify(cardService,Mockito.times(1)).getCardByNumber(eq(userDto.getCardNumber()));
        Assertions.assertEquals(expected,actual);


    }

    private User generateUser() {
        User user = new User();
        user.setEmail("mail@mail.com");
        user.setCity(generateCity());
        user.setCard(generateCard());
        user.setBirthday(LocalDate.of(2022, 1, 1));
        user.setName("Vasya");
        user.setPhone("+71234567890");
        user.setSex("man");
        user.setStatus(0);
        return user;
    }

    private UserDto generateUserDto() {
        UserDto userDto = new UserDto();
        userDto.setEmail("mail@mail.com");
        userDto.setCardNumber(123456);
        userDto.setCardQrCode("qwerty");
        userDto.setName("Vasya");
        userDto.setPhone("+71234567890");
        userDto.setSex("man");
        userDto.setBirthday(LocalDate.of(2022, 1, 1));
        userDto.setCity("SPB");
        userDto.setStatus("BRONZE");
        return userDto;
    }

    private City generateCity() {
        City city = new City();
        city.setId(1L);
        city.setTitle("SPB");
        return city;
    }

    private Card generateCard() {
        Card card = new Card();
        card.setId(1L);
        card.setCardNumber(123456);
        card.setQrCode("qwerty");
        return card;
    }

}
