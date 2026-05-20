package com.springboot.todo.exception;

public class ResourceIncorrectFormatException extends RuntimeException {
    public ResourceIncorrectFormatException(String message) {
        super(message);
    }
}
