package com.oshovskii.client.shell;

import com.oshovskii.client.shell.interfaces.ShellAuth;
import com.oshovskii.common.dto.LoginRequestDto;
import com.oshovskii.common.dto.LoginResponseDto;
import com.oshovskii.common.dto.ResponseDto;
import com.oshovskii.common.dto.TokenRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Slf4j
@ShellComponent
@RequiredArgsConstructor
public class ShellAuthImpl implements ShellAuth {
    private String accessToken;
    private String refreshToken;
    private final WebClient.Builder webClientBuilder;

    @Value("${auth.service.endpoints.login}")
    private String authServiceLoginEndpoint;

    @Value("${auth.service.endpoints.logout}")
    private String authServiceLogoutEndpoint;

    @Override
    @ShellMethod(value = "Login command", key = {"login", "l"})
    public String login(@ShellOption(value = "--u") String username, @ShellOption(value = "--p") String password) {

        log.info("[login] username {}", username);

        LoginRequestDto loginRequestDto = new LoginRequestDto(username, password);
        ResponseEntity<LoginResponseDto> loginResponseDto = webClientBuilder.build().post()
                .uri(authServiceLoginEndpoint)
                .bodyValue(loginRequestDto)
                .retrieve()
                .toEntity(LoginResponseDto.class)
                .block();


        if (Objects.nonNull(loginResponseDto) && loginResponseDto.getStatusCode().is2xxSuccessful()) {
            accessToken = Objects.requireNonNull(loginResponseDto.getBody()).getAccess_token();
            refreshToken = Objects.requireNonNull(loginResponseDto.getBody()).getRefresh_token();
            return accessToken;
        } else {
            return Objects.requireNonNull(loginResponseDto.getBody()).toString();
        }
    }

    @Override
    @ShellMethod(value = "Logout command", key = {"logout", "out"})
    public String logout() {
        if (getCurrentAccessToken().isEmpty()) {
            return "Please upgrade your authorization first.";
        }

        log.info("[Logout] refresh_token {}", getCurrentRefreshToken());

        TokenRequestDto tokenRequestDto = new TokenRequestDto(getCurrentRefreshToken());

        ResponseEntity<ResponseDto> responseDto = webClientBuilder.build().post()
                .uri(authServiceLogoutEndpoint)
                .bodyValue(tokenRequestDto)
                .retrieve()
                .toEntity(ResponseDto.class)
                .block();

        assert responseDto != null;
        if (responseDto.getStatusCode().is2xxSuccessful()) {
            refreshToken = null;
        }

        return Objects.requireNonNull(responseDto.getBody()).getMessage();
    }

    @Override
    public String getCurrentAccessToken() {
        if (Objects.isNull(accessToken)) {
            return "";
        }
        return accessToken;
    }

    @Override
    public String getCurrentRefreshToken() {
        if (Objects.isNull(refreshToken)) {
            return "";
        }
        return refreshToken;
    }
}
