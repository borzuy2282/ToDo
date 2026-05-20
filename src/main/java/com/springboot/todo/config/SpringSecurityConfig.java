package com.springboot.todo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SpringSecurityConfig {

    private UserDetailsService userDetailsService;

    public SpringSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

//    @Value("${app.security.default.name}")
//    private String defaultUsername;
//    @Value("${app.security.default.password}")
//    private String defaultPassword;
//    @Value("${app.security.admin.name}")
//    private String adminUsername;
//    @Value("${app.security.admin.password}")
//    private String adminPassword;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> {
//                        authorize.requestMatchers(HttpMethod.POST, "/api/v1/**").hasRole("ADMIN");
//                        authorize.requestMatchers(HttpMethod.PUT, "/api/v1/**").hasRole("ADMIN");
//                        authorize.requestMatchers(HttpMethod.DELETE, "/api/v1/**").hasRole("ADMIN");
//                        authorize.requestMatchers(HttpMethod.GET, "/api/v1/**").hasAnyRole("ADMIN", "USER");
//                        authorize.requestMatchers(HttpMethod.PATCH, "/api/v1/**").hasAnyRole("ADMIN", "USER");
                        authorize.anyRequest().authenticated();
                        })
        .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration){
        return configuration.getAuthenticationManager();
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails defaultUser = User.builder()
//                .username(defaultUsername)
//                .password(passwordEncoder().encode(defaultPassword))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username(adminUsername)
//                .password(passwordEncoder().encode(adminPassword))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(defaultUser, admin);
//    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
