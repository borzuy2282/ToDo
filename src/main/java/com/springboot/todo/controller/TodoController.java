package com.springboot.todo.controller;

import com.springboot.todo.dto.TodoDto;
import com.springboot.todo.exception.ErrorDetails;
import com.springboot.todo.exception.ResourceIncorrectFormatException;
import com.springboot.todo.exception.ResourceNotFoundException;
import com.springboot.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("create")
    public ResponseEntity<TodoDto> createTodo(@RequestBody TodoDto todoDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.createTodo(todoDto));
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("{id}")
    public ResponseEntity<TodoDto> getTodo(@PathVariable Long id){
        return ResponseEntity.ok(todoService.getTodo(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos(){
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<TodoDto> updateTodo(@PathVariable Long id,
                                              @RequestBody TodoDto todoDto){
        return ResponseEntity.ok(todoService.updateTodo(id, todoDto));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id){
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PatchMapping("{id}/done")
    public ResponseEntity<TodoDto> doneTodo(@PathVariable Long id){
        return ResponseEntity.ok(todoService.updateDoneTodo(id, Boolean.TRUE));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PatchMapping("{id}/undone")
    public ResponseEntity<TodoDto> undoneTodo(@PathVariable Long id){
        return ResponseEntity.ok(todoService.updateDoneTodo(id, Boolean.FALSE));
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> resourceNotFoundExceptionHandler(ResourceNotFoundException ex,
                                                                     WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                webRequest.getDescription(false),
                "RESOURCE_NOT_FOUND"
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(ResourceIncorrectFormatException.class)
    public ResponseEntity<ErrorDetails> resourceIncorrectFormatExceptionHandler(ResourceIncorrectFormatException ex,
                                                                            WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                webRequest.getDescription(false),
                "RESOURCE_INCORRECT_FORMAT"
        );
        return ResponseEntity.badRequest().body(errorDetails);
    }
}
