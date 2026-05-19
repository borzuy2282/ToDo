package com.springboot.todo.service;

import com.springboot.todo.dto.TodoDto;
import com.springboot.todo.entity.Todo;
import com.springboot.todo.exception.TodoIncorrectFormatException;
import com.springboot.todo.mapper.TodoMapper;
import com.springboot.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
    private final TodoMapper todoMapper;
    private final TodoRepository todoRepository;

    public TodoService(TodoMapper todoMapper, TodoRepository todoRepository) {
        this.todoMapper = todoMapper;
        this.todoRepository = todoRepository;
    }

    public TodoDto createTodo(TodoDto todoDto){
        if (todoDto.title() == null) throw new TodoIncorrectFormatException("Title is mandatory!");
        Todo todo = todoMapper.toEntity(todoDto);
        todo.setDone(false);
        Todo savedTodo = todoRepository.save(todo);
        return todoMapper.toDto(savedTodo);
    }
}
