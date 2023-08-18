package com.oshovskii.server.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Date;
import java.util.List;

import static com.oshovskii.server.controllers.TaskController.CLAIMS_PREFERRED_USERNAME;

public class JwtUtils {

    private JwtUtils() {
        // No-op
    }

    private static final long EXPIRATION_TIME = 60 * 60 * 24 * 10;
    private static final String SECRET = "ThisIsASecret";
    public static final String HEADER_AUTHORIZATION = "Authorization";

    public static String createToken(String username) {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.put(CLAIMS_PREFERRED_USERNAME, List.of(username));

        String jwt = Jwts.builder()
                .setClaims(map.toSingleValueMap())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return jwt;
    }
}
