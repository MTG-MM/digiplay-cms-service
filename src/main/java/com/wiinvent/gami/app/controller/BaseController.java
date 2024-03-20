package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.response.base.BaseResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Log4j2
public abstract class BaseController {


  protected ResponseEntity<BaseResponse> successApi(String message, Object data) {
    return ResponseEntity.ok(new BaseResponse(0, false, message, data));
  }

  protected ResponseEntity<BaseResponse> successApi(String message) {
    return ResponseEntity.ok(new BaseResponse(0, false, message, null));
  }

  protected ResponseEntity<BaseResponse> successApi(Object data) {
    return ResponseEntity.ok(new BaseResponse(0, false, null, data));
  }

  protected ResponseEntity<BaseResponse> errorApi(String message) {
    return ResponseEntity.ok(new BaseResponse(0, true, message, null));
  }

  protected ResponseEntity<BaseResponse> errorApi(Exception ex) {
    log.error("handle error ",ex);
    return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(0, true, ex.getMessage(), null));
  }

  protected ResponseEntity<BaseResponse> errorApi(String message, Object data) {
    return ResponseEntity.ok(new BaseResponse(0, true, message, data));
  }

  protected ResponseEntity<BaseResponse> errorApi(Integer code, String message) {
    return ResponseEntity.ok(new BaseResponse(code, true, message, null));
  }

  protected ResponseEntity<BaseResponse> errorApi(Integer code) {
    return ResponseEntity.ok(new BaseResponse(code, true, null, null));
  }

  protected ResponseEntity<BaseResponse> errorApiStatus500(String message) {
    return ResponseEntity.status(500).body(new BaseResponse<>(0, true, message, null));
  }
}

