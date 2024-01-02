package com.managersystem.admin.server.exception;

import com.managersystem.admin.server.exception.base.BaseException;
import com.managersystem.admin.server.exception.base.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends BaseException {
  @Serial
  private static final long serialVersionUID = 1L;

  public AuthenticationException(String exception) {
    super(exception, ErrorCode.UNAUTHORIZED);
  }
  public AuthenticationException(Integer code) {
    super("Error", code);
  }
  public AuthenticationException(String exception, Integer code) {
    super(exception, code);
  }

}
