package com.wiinvent.gami.domain.exception;

import com.wiinvent.gami.domain.exception.base.BaseException;
import com.wiinvent.gami.domain.exception.base.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequestFailedException extends BaseException {

  public RequestFailedException(String message) {
    super(message, ErrorCode.BAD_REQUEST);
  }

  public RequestFailedException() {
    super("Request failed", ErrorCode.BAD_REQUEST);
  }
}
