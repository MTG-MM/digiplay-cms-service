package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.app.controller.dto.LoginDto;
import com.wiinvent.gami.domain.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/vt/cms/auth")
public class AuthController {

  @Autowired
  AccountService accountService;


  @PostMapping("init")
  public ResponseEntity<?> initAccount() {
    return ResponseEntity.ok(accountService.initAdminAccount());
  }

  @PostMapping("login")
  public ResponseEntity<?> login(@RequestBody LoginDto dto) {
    return ResponseEntity.ok(accountService.login(dto));
  }

}
