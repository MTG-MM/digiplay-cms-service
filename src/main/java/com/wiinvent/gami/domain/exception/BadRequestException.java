package com.wiinvent.gami.domain.exception;

import com.wiinvent.gami.domain.exception.base.BaseException;
import com.wiinvent.gami.domain.exception.base.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends BaseException {
  private static final long serialVersionUID = 1L;

  public BadRequestException(String exception) {
    super(exception, ErrorCode.BAD_REQUEST);
  }
  public BadRequestException(Integer code) {
    super("Error", code);
  }
  public BadRequestException(String exception, Integer code) {
    super(exception, code);
  }

}
