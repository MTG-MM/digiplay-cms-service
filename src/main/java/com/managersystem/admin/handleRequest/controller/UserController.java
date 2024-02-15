package com.managersystem.admin.handleRequest.controller;

import com.managersystem.admin.handleRequest.controller.dto.AccountDto;
import com.managersystem.admin.handleRequest.controller.dto.UserInfoDto;
import com.managersystem.admin.server.security.UserDetailsImpl;
import com.managersystem.admin.server.service.RewardPoolService;
import com.managersystem.admin.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/mos/cms/user")
public class UserController {

  @Autowired RewardPoolService rewardPoolService;

  @PostMapping("spin")
  public ResponseEntity<?> getRandomItem(Authentication authentication, @RequestParam String code){
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    return ResponseEntity.ok(rewardPoolService.getRewardItem(userDetails.getId(), code));
  }
}
