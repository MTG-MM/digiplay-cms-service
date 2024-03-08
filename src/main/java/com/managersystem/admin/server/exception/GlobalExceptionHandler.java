package com.managersystem.admin.server.exception;

import com.managersystem.admin.server.exception.base.BaseException;
import com.managersystem.admin.server.exception.base.ErrorCode;
import com.managersystem.admin.server.exception.base.ExceptionResponse;
import com.managersystem.admin.server.utils.JsonParser;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {BadRequestException.class,})
  public void badRequestException(BaseException ex, ServletResponse response) throws IOException {
    handleException(response, ex);
  }

  @ExceptionHandler(value = {AuthenticationException.class, AuthenticationException.class})
  public void authenticationException(BaseException ex, HttpServletResponse response) throws IOException {
    handleException(response, ex);
  }

  @ExceptionHandler(value = Exception.class)
  public void internalException(BaseException ex, ServletResponse response) throws IOException {
    handleException(response, ex);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    BindingResult bindingResult = ex.getBindingResult();
    List<String> errorMessages = bindingResult.getFieldErrors().stream()
        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
        .toList();
    return new ResponseEntity<>(ExceptionResponse.createFrom(new BaseException(StringUtils.collectionToCommaDelimitedString(errorMessages), ErrorCode.NOT_VALID)), HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
    return new ResponseEntity<>(ExceptionResponse.createFrom(ex), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  public void handleException(ServletResponse response, BaseException exception) throws IOException {
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    final Map<String, Object> body = new HashMap<>();
    httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
    httpResponse.setStatus(exception.getCode());
    body.put("status", exception.getCode());
    body.put("message", exception.getMessage());

    JsonParser.writeValue(httpResponse.getOutputStream(), body);
  }
}
