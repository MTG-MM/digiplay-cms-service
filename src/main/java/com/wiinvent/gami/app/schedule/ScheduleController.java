package com.wiinvent.gami.app.schedule;

import com.wiinvent.gami.domain.service.RewardItemStatisticService;
import com.wiinvent.gami.domain.service.RewardScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/mos/cms/it/schedule")
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
