package ru.gb.lk_loyality.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.gb.lk_loyality.dto.UserDto;
import ru.gb.lk_loyality.entities.User;
import ru.gb.lk_loyality.repositories.UserRepository;
import ru.gb.lk_loyality.utils.MappingUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final MappingUtils mapper;


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
     * Spring Security
     * получение UserDetails по имени пользователя
     *
     * @param username имя пользователя
     * @return обьект UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
