package com.springboot.todo.service;

import com.springboot.todo.dto.LoginDto;
import com.springboot.todo.dto.RegisterDto;
import com.springboot.todo.entity.Role;
import com.springboot.todo.entity.User;
import com.springboot.todo.exception.DuplicateResourceException;
import com.springboot.todo.exception.ResourceApiException;
import com.springboot.todo.repository.RoleRepository;
import com.springboot.todo.repository.UserRepository;
import com.springboot.todo.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String register(RegisterDto registerDto){
        if(registerDto.email() == null || registerDto.username() == null || registerDto.name() == null || registerDto.password() == null){
            throw new ResourceApiException("Bad data was provided!");
        }
        if(userRepository.existsByEmail(registerDto.email())) throw new DuplicateResourceException("User with this email already exists!");
        if(userRepository.existsByUsername(registerDto.username())) throw new DuplicateResourceException("User with this username already exists!");
        User user = new User();
        user.setName(registerDto.name());
        user.setEmail(registerDto.email());
        user.setUsername(registerDto.username());
        user.setPassword(passwordEncoder.encode(registerDto.password()));
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName("ROLE_USER");
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);

        return "User registered successfully!";
    }

    public String login(LoginDto loginDto){
        if (loginDto.usernameOrEmail() == null || loginDto.password() == null){
            throw new ResourceApiException("Bad data was provided!");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.usernameOrEmail(),
                loginDto.password()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }
}
