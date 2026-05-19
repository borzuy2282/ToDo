package com.springboot.todo.dto;

public record TodoDto(
        Long id,
        String title,
        String description,
        boolean done
) {
}
