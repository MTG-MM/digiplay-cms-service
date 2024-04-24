package com.wiinvent.gami.domain.exception;

import com.wiinvent.gami.domain.exception.base.BaseException;
import com.wiinvent.gami.domain.exception.base.ErrorCode;
import com.wiinvent.gami.domain.exception.base.ErrorResponse;
import com.wiinvent.gami.domain.exception.base.ExceptionResponse;
import com.wiinvent.gami.domain.utils.Constants;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jshell.spi.ExecutionControl;
import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = {BaseException.class})
  public ResponseEntity<ErrorResponse> badRequestException(Exception ex, HttpServletResponse response) {
    return ResponseEntity.badRequest().body(new ErrorResponse(400, ex.getMessage()));
  }

  @ExceptionHandler(value = {AuthenticationException.class})
  public ResponseEntity<ErrorResponse> authenticationException(AuthenticationException exception, HttpServletResponse response) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(401, Constants.INVALID_TOKEN));
  }

  @ExceptionHandler(value = {ExecutionControl.InternalException.class, Exception.class})
  public ResponseEntity<ErrorResponse> internalServerException(Exception ex, HttpServletResponse response) {
    log.error("=====>internalServerException: ", ex);
    return ResponseEntity.internalServerError().body(new ErrorResponse(500, Constants.INTERNAL_SERVER_ERROR));
  }

  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
    BindingResult bindingResult = ex.getBindingResult();
    List<String> errorMessages = bindingResult.getFieldErrors().stream()
        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
        .toList();
    return new ResponseEntity<>(ExceptionResponse.createFrom(new BaseException(StringUtils.collectionToCommaDelimitedString(errorMessages), ErrorCode.NOT_VALID)), HttpStatus.BAD_REQUEST);
  }

}