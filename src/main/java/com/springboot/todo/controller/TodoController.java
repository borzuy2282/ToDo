package com.springboot.todo.controller;

import com.springboot.todo.dto.TodoDto;
import com.springboot.todo.exception.ErrorDetails;
import com.springboot.todo.exception.TodoIncorrectFormatException;
import com.springboot.todo.exception.TodoNotFoundException;
import com.springboot.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("create")
    public ResponseEntity<TodoDto> createTodo(@RequestBody TodoDto todoDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.createTodo(todoDto));
    }

    @GetMapping("{id}")
    public ResponseEntity<TodoDto> getTodo(@PathVariable Long id){
        return ResponseEntity.ok(todoService.getTodo(id));
    }

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<ErrorDetails> todoNotFoundExceptionHandler(TodoNotFoundException ex,
                                                                            WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                webRequest.getDescription(false),
                "TODO_NOT_FOUND"
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(TodoIncorrectFormatException.class)
    public ResponseEntity<ErrorDetails> todoIncorrectFormatExceptionHandler(TodoIncorrectFormatException ex,
                                                                            WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                webRequest.getDescription(false),
                "TODO_INCORRECT_FORMAT"
        );
        return ResponseEntity.badRequest().body(errorDetails);
    }
}
