package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.security.service.UserDetailsImpl;
import com.wiinvent.gami.domain.service.RewardPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vt/cms/user")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class UserController {

  @Autowired
  RewardPoolService rewardPoolService;


}
