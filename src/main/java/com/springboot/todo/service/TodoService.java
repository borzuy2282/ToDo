package com.springboot.todo.service;

import com.springboot.todo.dto.TodoDto;
import com.springboot.todo.entity.Todo;
import com.springboot.todo.exception.ResourceIncorrectFormatException;
import com.springboot.todo.exception.ResourceNotFoundException;
import com.springboot.todo.mapper.TodoMapper;
import com.springboot.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private final TodoMapper todoMapper;
    private final TodoRepository todoRepository;

    public TodoService(TodoMapper todoMapper, TodoRepository todoRepository) {
        this.todoMapper = todoMapper;
        this.todoRepository = todoRepository;
    }

    public TodoDto createTodo(TodoDto todoDto){
        if (todoDto.title() == null) throw new ResourceIncorrectFormatException("Title is mandatory to create todo!");
        Todo todo = todoMapper.toEntity(todoDto);
        todo.setDone(false);
        Todo savedTodo = todoRepository.save(todo);
        return todoMapper.toDto(savedTodo);
    }

    public TodoDto getTodo(Long id) {
        Todo todo = findTodoById(id);
        return todoMapper.toDto(todo);
    }

    public List<TodoDto> getAllTodos(){
        return todoRepository
                .findAll()
                .stream()
                .map(todoMapper::toDto)
                .toList();
    }

    public TodoDto updateTodo(Long id, TodoDto todoDto){
        Todo todo = findTodoById(id);
        if (todoDto.title() != null) todo.setTitle(todoDto.title());
        if (todoDto.description() != null) todo.setDescription(todoDto.description());
        Todo updatedTodo = todoRepository.save(todo);
        return todoMapper.toDto(updatedTodo);
    }

    public void deleteTodo(Long id){
        todoRepository.deleteById(id);
    }


    private Todo findTodoById(Long id){
        return todoRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Todo with id " + id + " was not found"));
    }
}
