package com.springboot.todo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginDto(
        @JsonProperty("login")
        String usernameOrEmail,
        String password
) {
}
