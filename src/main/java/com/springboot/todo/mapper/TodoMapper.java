package com.springboot.todo.mapper;

import com.springboot.todo.dto.TodoDto;
import com.springboot.todo.entity.Todo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    Todo toEntity(TodoDto todoDto);
    TodoDto toDto(Todo todo);
}
