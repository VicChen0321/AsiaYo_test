package com.asiayo.api.handler;

import com.asiayo.api.dto.ApiError;
import com.asiayo.api.exception.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = {ApiException.class})
  public ResponseEntity<ApiError> handleApiException(ApiException e) {
    ApiError error = new ApiError(e.getStatus().value(), e.getMsg());
    return new ResponseEntity<>(error, e.getStatus());

  }
}
