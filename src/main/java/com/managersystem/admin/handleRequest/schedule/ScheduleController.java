package com.managersystem.admin.handleRequest.schedule;

import com.managersystem.admin.server.service.RewardItemStatisticService;
import com.managersystem.admin.server.service.RewardScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/mos/it/schedule")
@CrossOrigin(origins = "*")
public class ScheduleController {

  @Autowired
  RewardScheduleService rewardScheduleService;
  @Autowired
  RewardItemStatisticService rewardItemStatisticService;

  @GetMapping("statistic/today")
  public void processTodayStatistic(){
    rewardItemStatisticService.processStatisticToday();
  }
  @GetMapping("reward/process-quantity")
  public void processRewardSchedule() {
    rewardScheduleService.addRewardSegmentQuantity();
  }
}
