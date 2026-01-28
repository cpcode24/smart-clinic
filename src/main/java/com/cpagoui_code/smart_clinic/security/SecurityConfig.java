package com.cpagoui_code.smart_clinic.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


/**
 * Central security configuration for the application.
 *
 * This class defines the web security rules (which endpoints require authentication
 * and which are publicly available) and configures form-based login.
 */
@Configuration
@EnableWebMvc
public class SecurityConfig {

    /**
     * Configure the application's SecurityFilterChain.
     *
     * The returned SecurityFilterChain controls access to URL patterns and sets up
     * the form-based login page. Adjust request matchers and authorities here
     * to change access rules for ADMIN and USER roles.
     *
     * @param http the HttpSecurity builder used to configure web based security for specific http requests
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs while building the SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/admin/**", "/appointment/**", "/doctor/**", "/clinic/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .requestMatchers("/", "/patient/**", "/appointment/**", "/doctor/**").hasAuthority("USER")
                .anyRequest().authenticated()
                .requestMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .permitAll()
            );
       
        return http.build();
    }

}

