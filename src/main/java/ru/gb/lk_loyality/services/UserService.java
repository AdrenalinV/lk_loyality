package ru.gb.lk_loyality.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.lk_loyality.dto.UserDto;
import ru.gb.lk_loyality.repositories.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
//    private final MappingUtils mappingUtils;

    public Optional<UserDto> getUserDtoById(Long id) {
        return userRepository.findById(id).map(UserDto::new);
    }

//    private UserDto toDto(User user) {
//        return mappingUtils.mapToUserDto(user);
//    }
//
//    private User toEntity(UserDto userDto) {
//        return mappingUtils.mapToUser(userDto);
//    }
}
