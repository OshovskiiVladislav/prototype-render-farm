package com.oshovskii.controllers;

import com.oshovskii.common.dto.UserExtraDto;
import com.oshovskii.model.UserExtra;
import com.oshovskii.services.interfaces.UserExtraService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/userextras")
public class UserExtraController {

    private final UserExtraService userExtraService;
    private static final JwtParser DEFAULT_PARSER = Jwts.parser();
    private static final String CLAIMS_PREFERRED_USERNAME = "preferred_username";

    @GetMapping("/me")
    public UserExtra getUserExtra(@RequestHeader(name = "Authorization") String authHeader) {
        log.info("Method getUserExtra called with JwtAuthenticationToken: {}", authHeader);
        return userExtraService.validateAndGetUserExtra(getAutHeaderClaimsUsername(authHeader));
    }

    @PostMapping("/me")
    public UserExtra saveUserExtra(@Valid @RequestBody(required = false) UserExtraDto updateUserExtraRequest,
                                   @RequestHeader(name = "Authorization") String authHeader) {
        log.info("Method saveUserExtra called with UserExtraDto: {} and JwtAuthenticationToken: {}",
                updateUserExtraRequest, authHeader
        );
        String username = getAutHeaderClaimsUsername(authHeader);
        Optional<UserExtra> userExtraOptional = userExtraService.getUserExtra(username);
        UserExtra userExtra = userExtraOptional.orElseGet(() -> new UserExtra(username));
        userExtra.setAvatar(updateUserExtraRequest.getAvatar());
        return userExtraService.saveUserExtra(userExtra);
    }

    private String getAutHeaderClaimsUsername(String authHeader) {
        String token = authHeader.substring(7);
        String[] splitToken = token.split("\\.");
        String unsignedToken = splitToken[0] + "." + splitToken[1] + ".";
        Jwt<?,?> jwt = DEFAULT_PARSER.parse(unsignedToken);
        Claims claims = (Claims) jwt.getBody();
        String userName = (String) claims.get(CLAIMS_PREFERRED_USERNAME);
        log.info("{}", userName);
        return userName;
    }

}
