package com.cpagoui_code.smart_clinic.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebMvc
public class SecurityConfig {
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

