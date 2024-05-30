package com.wiinvent.gami.app.controller.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wiinvent.gami.domain.entities.type.UserType;
import com.wiinvent.gami.domain.response.UserResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("v1/portal/user")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class UserController {

  @Autowired UserService userService;

  @GetMapping("/{id}")
  public ResponseEntity<?> getDetailUser(@PathVariable UUID id) throws IOException {
    return ResponseEntity.ok(userService.getUserDetail(id));
  }

  @GetMapping("")
  public ResponseEntity<PageCursorResponse<UserResponse>> getAllUsers(
      @RequestParam(required = false) UUID userId,
      @RequestParam(required = false) String displayName,
      @RequestParam(required = false) String phoneNumber,
      @RequestParam(required = false) Integer level,
      @RequestParam(required = false) UserType type,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false, defaultValue = "20") Integer limit,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate gte,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate lte
  ) {
    return ResponseEntity.ok(userService.getPageUser(userId, displayName, phoneNumber, level, type, next, pre, limit, gte, lte));
  }

}
