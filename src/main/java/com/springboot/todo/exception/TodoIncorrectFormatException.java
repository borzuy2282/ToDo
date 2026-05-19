package com.springboot.todo.exception;

public class TodoIncorrectFormatException extends RuntimeException {
    public TodoIncorrectFormatException(String message) {
        super(message);
    }
}
