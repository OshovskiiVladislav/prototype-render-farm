package com.oshovskii.controllers;

import com.oshovskii.common.dto.*;
import com.oshovskii.services.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login (@RequestBody LoginRequestDto loginRequestDto) {
        log.info("login with LoginRequestDto: " + loginRequestDto);
        return authService.login(loginRequestDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto> logout (@RequestBody TokenRequestDto token) {
        log.info("logout with TokenRequestDto: " + token);
        return authService.logout(token);
    }

    @PostMapping("/introspect")
    public ResponseEntity<IntrospectResponseDto> introspect(@RequestBody TokenRequestDto token) {
        log.info("introspect with TokenRequestDto: " + token);
        return authService.introspect(token);
    }
}
