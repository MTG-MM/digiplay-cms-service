package com.managersystem.admin.server.exception.base;

import lombok.Data;

@Data
public class BaseException extends RuntimeException {

  protected int code;

  public BaseException(String message, int code) {
    super(message);
    setCode(code);
  }
}
