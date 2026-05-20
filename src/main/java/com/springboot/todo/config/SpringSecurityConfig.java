package com.springboot.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http){

        http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests((authorize) ->
                authorize.anyRequest().authenticated())
        .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
