package ru.gb.lk_loyality.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gb.lk_loyality.dto.UserDto;
import ru.gb.lk_loyality.entities.City;
import ru.gb.lk_loyality.entities.Role;
import ru.gb.lk_loyality.entities.User;
import ru.gb.lk_loyality.repositories.RoleRepository;
import ru.gb.lk_loyality.repositories.UserRepository;
import ru.gb.lk_loyality.utils.UserMapper;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CardService cardService;
    private final CityService cityService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    /**
     * дефолтное значение города
     */
    @Value("${app.city}")
    private String DEFAULT_CITY;


    /**
     * метод получения UserDto по id user
     *
     * @param id идентификатор пользователя
     * @return UserDto
     */
    public Optional<UserDto> getUserDtoById(Long id) {
        return userRepository.findById(id).map(user -> mapper.mapToUserDto(user));
    }

    /**
     * получение User по имени пользователя
     *
     * @param username имя пользователя
     * @return обьект User
     * @throws UsernameNotFoundException
     */

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    /**
     * метод сохранения объекта user
     *
     * @param user - объект пользователя для сохранеения
     * @return сохраненный объект пользователя
     */
    public User saveUser(User user) {
        if(user.getRoles()==null || user.getRoles().isEmpty()){
            Role role = roleRepository.findByName("ROLE_USER");
            user.setRoles(Collections.singletonList(role));
        }
        if (user.getCity()==null || user.getCity().getTitle().isBlank()){
            City city = cityService.getCityByTitle(DEFAULT_CITY);
            if (city == null || city.getTitle().isBlank()) {
                city = new City();
                city.setTitle(DEFAULT_CITY);
                city = cityService.save(city);
            }
            user.setCity(city);
        }
        if(user.getCard()==null){
            user.setCard(cardService.createCard());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getStatus()==null){
            user.setStatus(User.Status.BRONZE.ordinal());
        }
        return userRepository.save(user);
    }

    /**
     * метод возвращает пользователя по имени и паролю
     *
     * @param userName имя пользователя в базе данных
     * @param password пароль пользователя
     * @return найденый пользователь, или null
     */
    public User findByNameAndPassword(String userName, String password) {
        User user = loadUserByUsername(userName);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }
}
