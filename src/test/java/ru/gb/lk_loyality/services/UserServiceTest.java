package ru.gb.lk_loyality.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.gb.lk_loyality.dto.UserDto;
import ru.gb.lk_loyality.entities.Card;
import ru.gb.lk_loyality.entities.City;
import ru.gb.lk_loyality.entities.Role;
import ru.gb.lk_loyality.entities.User;
import ru.gb.lk_loyality.repositories.RoleRepository;
import ru.gb.lk_loyality.repositories.UserRepository;
import ru.gb.lk_loyality.utils.MappingUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;


public class UserServiceTest {
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final CardService cardService = Mockito.mock(CardService.class);
    private final CityService cityService = Mockito.mock(CityService.class);
    private final RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
    private final MappingUtils mapper = Mockito.mock(MappingUtils.class);
    private final PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
    private final UserService userService = new UserService(userRepository,
            cardService,
            cityService,
            roleRepository,
            encoder,
            mapper);

    @Test
    @DisplayName("Тестирование получения UserDto по user id")
    void getUserDtoByIdTest() {
        User user = generateUser();

        Mockito.when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        Mockito.when(mapper.mapToUserDto(any(User.class))).thenReturn(generateUserDto());

        Optional<UserDto> expect = userService.getUserDtoById(1L);
        Optional<UserDto> actual = Optional.of(generateUserDto());

        Mockito.verify(userRepository).findById(eq(1L));
        Mockito.verify(mapper).mapToUserDto(eq(user));
        Assertions.assertEquals(expect, actual);
    }

    @Test
    @DisplayName("Проверка сохранения нового объекта")
    void saveNewUser(){
        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");
        User user = new User();
        user.setName("Vadim");
        user.setEmail("mail@mail.ru");
        user.setPassword("test");

        Mockito.when(roleRepository.findByName("ROLE_USER")).thenReturn(role);
        Mockito.when(cityService.getCityByTitle(any())).thenReturn(generateCity());
        Mockito.when(cardService.createCard()).thenReturn(generateCard());

        userService.saveUser(user);

        Mockito.verify(roleRepository).findByName("ROLE_USER");
        Mockito.verify(cityService).getCityByTitle(any());
        Mockito.verify(cardService).createCard();
        Mockito.verify(userRepository).save(any(User.class));
    }
    @Test
    @DisplayName("Проверка сохранения нового объекта")
    void saveUser(){

        User user = generateUser();
        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");
        user.setRoles(Collections.singletonList(role));
        userService.saveUser(user);

        Mockito.verify(roleRepository, Mockito.never()).findByName(any());
        Mockito.verify(cityService, Mockito.never()).getCityByTitle(any());
        Mockito.verify(cardService, Mockito.never()).createCard();
        Mockito.verify(userRepository).save(eq(user));
    }

    private User generateUser() {
        User user = new User();
        user.setId(1L);
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
