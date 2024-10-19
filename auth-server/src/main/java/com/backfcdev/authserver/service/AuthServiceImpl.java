package com.backfcdev.authserver.service;

import com.backfcdev.authserver.entities.UserEntity;
import com.backfcdev.authserver.helper.JwtHelper;
import com.backfcdev.authserver.dtos.TokenDto;
import com.backfcdev.authserver.dtos.UserDto;
import com.backfcdev.authserver.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;


@Transactional
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtHelper jwtHelper;

    private static final String USER_EXCEPTION_MSG = "Error to auth user";


    @Override
    public TokenDto login(UserDto user) {
        final var userFromDB = this.userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new ResponseStatusException(UNAUTHORIZED, USER_EXCEPTION_MSG));
        this.validPassword(user, userFromDB);
        return TokenDto.builder()
                .tokenAccess(this.jwtHelper.createToken(userFromDB.getUsername())).build();
    }

    @Override
    public TokenDto validateToken(TokenDto token) {
        if (this.jwtHelper.validateToken(token.getTokenAccess())) {
            return TokenDto.builder()
                    .tokenAccess(this.jwtHelper.createToken(token.getTokenAccess())).build();
        }
        throw new ResponseStatusException(UNAUTHORIZED, USER_EXCEPTION_MSG);
    }


    private void validPassword(UserDto userDto, UserEntity userEntity) {
        if (!this.passwordEncoder.matches(userDto.getPassword(), userEntity.getPassword())) {
            throw new ResponseStatusException(UNAUTHORIZED, USER_EXCEPTION_MSG);
        }
    }
}
