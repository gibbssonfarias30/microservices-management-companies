package com.backfcdev.authserver.service;

import com.backfcdev.authserver.dtos.TokenDto;
import com.backfcdev.authserver.dtos.UserDto;

public interface AuthService {

    TokenDto login(UserDto user);
    TokenDto validateToken(TokenDto token);
}
