package com.wiinvent.gami.app.controller.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wiinvent.gami.domain.response.UserResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("api/vt/cms/user")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class UserController {

  @Autowired UserService userService;

  @GetMapping("")
  public ResponseEntity<PageCursorResponse<UserResponse>> getAllUsers(
      @RequestParam(required = false) UUID userId,
      @RequestParam(required = false) String phoneNumber,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false, defaultValue = "20") Integer limit,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate gte,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate lte
  ) {
    return ResponseEntity.ok(userService.getPageUser(userId, phoneNumber, next, pre, limit, gte, lte));
  }
}
