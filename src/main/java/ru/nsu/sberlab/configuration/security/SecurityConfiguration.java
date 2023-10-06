package ru.nsu.sberlab.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private static final String[] ENDPOINTS_WHITELIST = {
            "/",
            "/user/registration",
            "/pet/find",
            "/css/**",
            "/img/**",
            "/js/**"
    };
    private static final String LOGIN_PAGE = "/user/login";
    private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String DEFAULT_LOGOUT_URL = "/logout";
    private static final String DEFAULT_LOGIN_SUCCESS_URL = "/user/personal-cabinet";
    private static final String DEFAULT_LOGOUT_SUCCESS_URL = "/";
    private static final String JSESSION_COOKIE = "JSESSIONID";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
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
                        .defaultSuccessUrl(DEFAULT_LOGIN_SUCCESS_URL, true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher(DEFAULT_LOGOUT_URL))
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies(JSESSION_COOKIE)
                        .logoutSuccessUrl(DEFAULT_LOGOUT_SUCCESS_URL));
        return httpSecurity.build();
    }
}
