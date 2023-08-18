package com.oshovskii.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.cors.reactive.CorsConfigurationSource;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.oauth2.jwt.Jwt;

import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Configuration
//@EnableWebSecurity
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource;
    private static final String TASKS_MANAGER = "TASKS_MANAGER";

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity,
                                                            Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthConverter) {
        return serverHttpSecurity
                // https://stackoverflow.com/questions/56056404/disable-websession-creation-when-using-spring-security-with-spring-webflux
                //https://github.com/spring-projects/spring-security/issues/7157
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())

                .httpBasic().disable()

                .csrf().disable()

                .cors().configurationSource(corsConfigurationSource)
                .and()

                .authorizeExchange(exchange ->
                        exchange.pathMatchers("/eureka/**")
                                .permitAll()
                                .pathMatchers("/api/*/auth/**").permitAll()
                                .pathMatchers("/api/*/tasks/nickname").hasAuthority(TASKS_MANAGER)
                                .anyExchange()
                                .authenticated())
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(
                        jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)))
                //.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .build();
    }

}
