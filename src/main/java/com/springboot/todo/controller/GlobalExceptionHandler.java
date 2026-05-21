package com.springboot.todo.controller;

import com.springboot.todo.exception.DuplicateResourceException;
import com.springboot.todo.exception.ErrorDetails;
import com.springboot.todo.exception.ResourceApiException;
import com.springboot.todo.exception.ResourceIncorrectFormatException;
import com.springboot.todo.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
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

    @ExceptionHandler(ResourceApiException.class)
    public ResponseEntity<ErrorDetails> resourceApiExceptionHandler(ResourceApiException ex,
                                                                    WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                webRequest.getDescription(false),
                "RESOURCE_BAD_API_REQUEST"
        );
        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorDetails> duplicateResourceExceptionHandler(DuplicateResourceException ex,
                                                                          WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                webRequest.getDescription(false),
                "DUPLICATE_RESOURCE"
        );
        return ResponseEntity.badRequest().body(errorDetails);
    }
}
