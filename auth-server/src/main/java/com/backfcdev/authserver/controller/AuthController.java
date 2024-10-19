package com.backfcdev.authserver.controller;


import com.backfcdev.authserver.dtos.TokenDto;
import com.backfcdev.authserver.dtos.UserDto;
import com.backfcdev.authserver.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    ResponseEntity<TokenDto> jwtCreate(@RequestBody UserDto user) {
        return ResponseEntity.ok(this.authService.login(user));
    }


    @PostMapping("/jwt")
    ResponseEntity<TokenDto> jwtValidate(@RequestHeader String accessToken) {
        return ResponseEntity.ok(this.authService.validateToken(TokenDto.builder().tokenAccess(accessToken).build()));
    }
}
