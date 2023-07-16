package ru.nsu.sberlab.configurations.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private static final String[] ENDPOINTS_WHITELIST = {
            "/",
            "/user/registration",
            "/pet/find",
            "/img/**",
            "/css/**"
    };
    private static final String LOGIN_PAGE = "/user/login";
    private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String DEFAULT_SUCCESS_URL = "/";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                .disable()
                .authorizeHttpRequests(request -> request
                        .requestMatchers(ENDPOINTS_WHITELIST)
                        .permitAll()
                        .requestMatchers("/pet/privileged-list")
                        .hasAnyRole("PRIVILEGED_ACCESS", "ADMIN")
                        .anyRequest()
                        .authenticated())
                .formLogin(form -> form
                        .loginPage(LOGIN_PAGE)
                        .loginProcessingUrl(LOGIN_PROCESSING_URL)
                        .defaultSuccessUrl(DEFAULT_SUCCESS_URL)
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl(DEFAULT_SUCCESS_URL));
        return httpSecurity.build();
    }
}