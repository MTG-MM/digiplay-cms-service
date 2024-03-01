package com.managersystem.admin.handleRequest.controller;

import com.managersystem.admin.handleRequest.controller.base.BaseController;
import com.managersystem.admin.handleRequest.controller.dto.AccountDto;
import com.managersystem.admin.handleRequest.controller.dto.LoginDto;
import com.managersystem.admin.server.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/mos/cms/auth")
public class AccountController extends BaseController {

  @Autowired
  AccountService accountService;


  @PostMapping("init")
  public ResponseEntity<?> initAccount() {
    try {
      return ResponseEntity.ok(accountService.initAdminAccount());
    } catch (Exception ex) {
      return errorApi(ex);
    }
  }

  @PostMapping("sign-up")
  public ResponseEntity<?> createAccount(@RequestBody @Valid AccountDto dto) {
    try {
      return ResponseEntity.ok(accountService.createAccount(dto));
    } catch (Exception ex) {
      return errorApi(ex);
    }
  }

  @PostMapping("login")
  public ResponseEntity<?> login(@RequestBody LoginDto dto) {

      return ResponseEntity.ok(accountService.login(dto));

  }
}
