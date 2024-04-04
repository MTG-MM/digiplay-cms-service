package com.wiinvent.gami.app.schedule;

import com.wiinvent.gami.domain.service.reward.RewardItemStatisticService;
import com.wiinvent.gami.domain.service.reward.RewardScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vt/cms/it/schedule")
@CrossOrigin(origins = "*")
public class ScheduleController {

  private final RewardScheduleService rewardScheduleService;
  private final RewardItemStatisticService rewardItemStatisticService;

  public ScheduleController(RewardScheduleService rewardScheduleService, RewardItemStatisticService rewardItemStatisticService) {
    this.rewardScheduleService = rewardScheduleService;
    this.rewardItemStatisticService = rewardItemStatisticService;
  }

  @GetMapping("statistic/today")
  public void processTodayStatistic(){
    rewardItemStatisticService.processStatisticToday();
  }
  @GetMapping("reward/process-quantity")
  public void processRewardSchedule() {
    rewardScheduleService.addRewardSegmentQuantity();
  }
}
