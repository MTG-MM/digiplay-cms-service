package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.service.reward.RewardPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vt/cms/user")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class UserController {

  @Autowired
  RewardPoolService rewardPoolService;


}
