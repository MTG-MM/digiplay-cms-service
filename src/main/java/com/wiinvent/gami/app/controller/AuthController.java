package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.dto.LoginDto;
import com.wiinvent.gami.domain.exception.base.NotAuthorizedException;
import com.wiinvent.gami.domain.service.AccountService;
import com.wiinvent.gami.domain.service.GoogleAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/portal/auth")
public class AuthController {

  @Autowired
  AccountService accountService;

  @PostMapping("init")
  public ResponseEntity<?> initAccount() {
    return ResponseEntity.ok(accountService.initAdminAccount());
  }

  @PostMapping("login")
  public ResponseEntity<?> login(@RequestBody LoginDto dto,
                                 @RequestParam(required = false) String sign) {

    return ResponseEntity.ok(accountService.login(dto, sign));
  }
}
