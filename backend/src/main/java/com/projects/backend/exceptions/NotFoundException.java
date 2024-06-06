package com.projects.backend.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends Exception{
  private static final HttpStatus statusCode = HttpStatus.NOT_FOUND;
  public NotFoundException(String entityName, Long id) {
    super(String.format("could not find %s of id %s", entityName, id));
  }

  public static HttpStatus getsStatusCode() {
    return statusCode;
  }
}
