package com.springboot.todo.config;

import com.springboot.todo.security.JwtAuthenticationEntryPoint;
import com.springboot.todo.security.JwtAuthenticationFilter;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SpringSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAuthenticationFilter authenticationFilter;

    public SpringSecurityConfig(UserDetailsService userDetailsService, JwtAuthenticationEntryPoint entryPoint, JwtAuthenticationFilter authenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = entryPoint;
        this.authenticationFilter = authenticationFilter;
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
                        authorize.requestMatchers("/api/v1/auth/**").permitAll();
                        authorize.anyRequest().authenticated();
                        })
        .httpBasic(Customizer.withDefaults());

        http.exceptionHandling( exception -> exception
                .authenticationEntryPoint(authenticationEntryPoint)
        );

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
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
