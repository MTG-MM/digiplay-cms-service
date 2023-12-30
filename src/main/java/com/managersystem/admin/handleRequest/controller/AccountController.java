package com.managersystem.admin.handleRequest.controller;

import com.managersystem.admin.handleRequest.controller.dto.*;
import com.managersystem.admin.server.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mos/cms/auth")
public class AccountController {

  @Autowired
  AccountService accountService;


  @PostMapping("init")
  public ResponseEntity<?> initAccount(){
    return ResponseEntity.ok(accountService.initAdminAccount());
  }

  @PostMapping("sign-in")
  public ResponseEntity<?> createAccount(@RequestBody AccountDto dto){
    return ResponseEntity.ok(accountService.createAccount(dto));
  }

  @PostMapping("login")
  public ResponseEntity<?> login(@RequestBody LoginDto dto){
    return ResponseEntity.ok(accountService.login(dto));
  }
}
