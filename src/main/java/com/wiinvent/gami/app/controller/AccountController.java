package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.app.controller.base.BaseController;
import com.wiinvent.gami.app.controller.dto.AccountDto;
import com.wiinvent.gami.app.controller.response.AccountResponse;
import com.wiinvent.gami.app.controller.response.base.PageResponse;
import com.wiinvent.gami.domain.security.service.UserDetailsImpl;
import com.wiinvent.gami.domain.service.AccountService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/vt/cms/account")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class AccountController extends BaseController {

  @Autowired AccountService accountService;

  @PostMapping("")
  @PreAuthorize("hasRole('PUBLISHER')" )
  public ResponseEntity<?> createAccount(Authentication authentication, @RequestBody AccountDto dto) {
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    accountService.createAccount(userDetails, userDetails.getAccountRole(), dto);
    return ResponseEntity.ok(true);
  }

  @GetMapping("")
  @PageableAsQueryParam
  public ResponseEntity<PageResponse<AccountResponse>> getAllAccount(
      @RequestParam(required = false) String userName,
      @RequestParam(required = false) UUID teamId,
      @Parameter(hidden = true) Pageable pageable) {
    return ResponseEntity.ok(PageResponse.createFrom(accountService.getAllPage(userName, teamId, pageable)));
  }
}
