package com.springboot.todo.service;

import com.springboot.todo.dto.RegisterDto;
import com.springboot.todo.entity.Role;
import com.springboot.todo.entity.User;
import com.springboot.todo.exception.DuplicateResourceException;
import com.springboot.todo.exception.ResourceApiException;
import com.springboot.todo.repository.RoleRepository;
import com.springboot.todo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
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
}
