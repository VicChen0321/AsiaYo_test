package com.asiayo.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

  private final HttpStatus status;
  private final String msg;

  public ApiException(HttpStatus status, String msg) {
    super(msg);
    this.status = status;
    this.msg = msg;
  }

}
