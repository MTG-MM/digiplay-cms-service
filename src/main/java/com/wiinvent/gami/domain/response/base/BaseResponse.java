package com.wiinvent.gami.domain.response.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
  int code;

  boolean error;

  String message;

  T data;

}
