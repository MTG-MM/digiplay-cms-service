package com.wiinvent.gami.domain.exception;

import com.wiinvent.gami.domain.exception.base.BaseException;
import com.wiinvent.gami.domain.exception.base.ErrorCode;
import com.wiinvent.gami.domain.exception.base.ExceptionResponse;
import com.wiinvent.gami.domain.utils.Constant;
import com.wiinvent.gami.domain.utils.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Log4j2
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  public void authenticationException(BaseException ex, ServletResponse response) throws IOException {
    handleException(response, ex);
  }
  @ExceptionHandler({BaseException.class})
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ResponseEntity<ExceptionResponse> baseException(Exception ex, WebRequest request) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(400, ex.getMessage()));
  }

  @ExceptionHandler({AccessDeniedException.class})
  @ResponseStatus(value = HttpStatus.FORBIDDEN)
  public ResponseEntity<ExceptionResponse> accessDeniedException(Exception ex, WebRequest request) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionResponse(403, Constant.INVALID_PERMISSION));
  }

  @ExceptionHandler({Exception.class})
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ExceptionResponse> internalServerErrorException(Exception ex, WebRequest request) {
    log.error("internalServerErrorException : ", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(500, Constant.INTERNAL_SERVER_ERROR));
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
