package ru.gb.lk_loyality.security.repositories;

import ru.gb.lk_loyality.security.UserInfo;

public interface JwtTokenService {
    String generateToken(UserInfo user);

    UserInfo parseToken(String Token);



}
