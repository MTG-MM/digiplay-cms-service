package com.managersystem.admin.server.exception.base;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
public class ExceptionResponse {
  private int code;
  private String message;

  public static ExceptionResponse createFrom(BaseException e) {
    ExceptionResponse response = new ExceptionResponse();
    response.setMessage(e.getMessage());
    response.setCode(e.getCode());
    // log.error(e);
    return response;
  }

  public static ExceptionResponse createFrom(Exception e) {
    ExceptionResponse response = new ExceptionResponse();
    response.setMessage(e.getMessage());
    response.setCode(500);
    // log.error(e);
    return response;
  }
}
