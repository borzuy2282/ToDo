package com.springboot.todo.exception;

public class ResourceApiException extends RuntimeException {
  public ResourceApiException(String message) {
    super(message);
  }
}
