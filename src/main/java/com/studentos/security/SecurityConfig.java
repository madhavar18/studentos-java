package com.studentos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Bean 1: The SecurityFilterChain
    // This is the main configuration — defines the rules
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                // WHY disable CSRF: REST APIs use stateless tokens, not browser sessions
                // CSRF attacks exploit browser-based session cookies — not applicable here

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // WHY STATELESS: server stores no session data
                // Each request carries its own authentication (the JWT token)
                // This enables horizontal scaling — any server can handle any request

                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/auth/**").permitAll()
                                .anyRequest().authenticated()
                );

        return http.build();
    }

    // Bean 2: Password encoder
    // @Bean makes this available for injection anywhere in your application
    // WHY declare as a Bean: Spring Security needs to know which encoder to use
    // Your AuthService also needs it to hash passwords — same bean, injected there
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    // Bean 3: AuthenticationProvider
    // Connects Spring Security to your database via UserDetailsService
    // DaoAuthenticationProvider: uses a UserDetailsService + PasswordEncoder
    // When login attempt comes in:
    //   1. Loads user from DB via UserDetailsService.loadUserByUsername()
    //   2. Checks submitted password against stored hash via PasswordEncoder.matches()
    //   3. Returns authenticated token if match, throws exception if not
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // Bean 4: AuthenticationManager
    // The entry point for programmatic authentication
    // You will use this in your AuthController on Day 30 for the login endpoint
    // Getting it from AuthenticationConfiguration is the correct Spring Boot 3 way
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
