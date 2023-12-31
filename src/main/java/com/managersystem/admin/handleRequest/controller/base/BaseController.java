package com.managersystem.admin.handleRequest.controller.base;

import com.managersystem.admin.handleRequest.controller.response.base.BaseResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
public class BaseController {


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

