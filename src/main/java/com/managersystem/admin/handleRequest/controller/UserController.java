package com.managersystem.admin.handleRequest.controller;

import com.managersystem.admin.handleRequest.controller.dto.AccountDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mos/cms/user")
public class UserController {

  @PostMapping("")
  public ResponseEntity<?> createAccount(Authentication authentication,  @RequestBody AccountDto dto){
    return ResponseEntity.ok(true);
  }

}
