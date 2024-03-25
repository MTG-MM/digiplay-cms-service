package com.wiinvent.gami.app.controller.user;

import com.wiinvent.gami.domain.response.UserResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.service.reward.RewardPoolService;
import com.wiinvent.gami.domain.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vt/cms/user")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class UserController {

  @Autowired UserService userService;

  @GetMapping("")
  public ResponseEntity<PageCursorResponse<UserResponse>> getAllUsers(
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false, defaultValue = "20") int limit
  ) {
    return ResponseEntity.ok(userService.getAllUsers(next, pre, limit));
  }
}
