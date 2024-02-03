package com.managersystem.admin.handleRequest.schedule;

import com.managersystem.admin.server.service.RewardScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/mos/it/reward-schedule")
@CrossOrigin(origins = "*")
public class RewardScheduleController {

  @Autowired
  RewardScheduleService rewardScheduleService;

  @PostMapping("process")
  public void processRewardSchedule() {
    rewardScheduleService.addRewardSegmentQuantity();
  }
}
