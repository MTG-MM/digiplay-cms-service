package com.wiinvent.gami.domain.exception.base;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceNotFoundException extends BaseException {
  private static Map<String, String> lang = new HashMap<>();
  static {
    lang.put("vi", "Không tồn tại dữ liệu");
    lang.put("en", "Resource not found");
  }

  private static final long serialVersionUID = 1L;

  public ResourceNotFoundException(String exception) {
    super(exception, ErrorCode.BAD_REQUEST);
  }

  public ResourceNotFoundException() {
    super(lang.get("vi"), ErrorCode.BAD_REQUEST);
  }
}
