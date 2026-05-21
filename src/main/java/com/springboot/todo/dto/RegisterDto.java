package com.springboot.todo.dto;

public record RegisterDto(
        String name,
        String username,
        String email,
        String password
) {
}
