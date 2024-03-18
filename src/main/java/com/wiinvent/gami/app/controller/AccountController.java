package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.app.controller.base.BaseController;
import com.wiinvent.gami.app.controller.dto.AccountDto;
import com.wiinvent.gami.domain.security.service.UserDetailsImpl;
import com.wiinvent.gami.domain.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("vt/cms/account")
public class AccountController extends BaseController {

  @Autowired AccountService accountService;

  @PostMapping("")
  public ResponseEntity<?> createAccount(Authentication authentication, @RequestBody AccountDto dto) {
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    accountService.createAccount(userDetails.getUsername(), userDetails.getAccountRole(), dto);
    return ResponseEntity.ok(true);
  }


}
