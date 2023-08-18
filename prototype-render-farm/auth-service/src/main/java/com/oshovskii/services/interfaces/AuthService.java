package com.oshovskii.services.interfaces;

import com.oshovskii.common.dto.*;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<LoginResponseDto> login(LoginRequestDto loginRequest);
    ResponseEntity<ResponseDto> logout(TokenRequestDto request);
    ResponseEntity<IntrospectResponseDto> introspect(TokenRequestDto request);
}
