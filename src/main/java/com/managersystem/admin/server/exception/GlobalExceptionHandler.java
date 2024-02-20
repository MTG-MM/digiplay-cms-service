package com.managersystem.admin.server.exception;

import com.managersystem.admin.server.exception.base.BaseException;
import com.managersystem.admin.server.exception.base.ErrorCode;
import com.managersystem.admin.server.exception.base.ExceptionResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {BadRequestException.class,})
  public ResponseEntity<?> badRequestException(BaseException ex, WebRequest request) {
    return new ResponseEntity<>(ExceptionResponse.createFrom(ex), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {AuthenticationException.class,})
  public ResponseEntity<?> authenticationException(BaseException ex, WebRequest request) {
    return new ResponseEntity<>(ExceptionResponse.createFrom(ex), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<?> internalException(BaseException ex, WebRequest request) {
    return new ResponseEntity<>(ExceptionResponse.createFrom(ex), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    BindingResult bindingResult = ex.getBindingResult();
    List<String> errorMessages = bindingResult.getFieldErrors().stream()
        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
        .toList();
    return new ResponseEntity<>(ExceptionResponse.createFrom(new BaseException(StringUtils.collectionToCommaDelimitedString(errorMessages), ErrorCode.NOT_VALID)), HttpStatus.BAD_REQUEST);
  }
}
