package com.oshovskii.discovery.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * https://stackoverflow.com/questions/46131196/com-netflix-discovery-shared-transport-transportexception-cannot-execute-reques
 * https://cloud.spring.io/spring-cloud-static/Greenwich.SR2/single/spring-cloud.html#_securing_the_eureka_server
 *  **Client registration eureka service failure registration failed Cannot execute request on any known server**
 *
 *  Spring security implemented on Eureka server require a valid CSRF token be sent
 *  with every request. Eureka clients will not generally possess a valid cross site request
 *  forgery (CSRF) token. So you will need to disable this requirement for the /eureka/** endpoints.
 *
 *  After disabling the CSRF token with follwoing line of code my Eureka client successfully register
 *  them selves with Eureka Server.
 */
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().ignoringRequestMatchers("/eureka/**");
        return httpSecurity.build();
    }
}
