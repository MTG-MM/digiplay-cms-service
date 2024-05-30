package com.wiinvent.gami.app.schedule;

import com.wiinvent.gami.domain.service.reward.RewardItemStatisticService;
import com.wiinvent.gami.domain.service.reward.RewardScheduleService;
import com.wiinvent.gami.domain.service.statistic.StatisticRevenueService;
import com.wiinvent.gami.domain.service.statistic.StatisticSubService;
import com.wiinvent.gami.domain.service.statistic.StatisticUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/portal/it/schedule")
@CrossOrigin(origins = "*")
public class ScheduleController {

  private final RewardScheduleService rewardScheduleService;
  private final RewardItemStatisticService rewardItemStatisticService;
  private final StatisticUserService statisticUserService;
  private final StatisticSubService statisticSubService;
  private final StatisticRevenueService statisticRevenueService;

  public ScheduleController(RewardScheduleService rewardScheduleService, RewardItemStatisticService rewardItemStatisticService
      , StatisticUserService statisticUserService, StatisticRevenueService statisticRevenueService, StatisticSubService statisticSubService) {
    this.rewardScheduleService = rewardScheduleService;
    this.rewardItemStatisticService = rewardItemStatisticService;
    this.statisticUserService = statisticUserService;
    this.statisticRevenueService = statisticRevenueService;
    this.statisticSubService = statisticSubService;
  }

  @PostMapping("statistic/today")
  public ResponseEntity<?> processTodayStatistic(){
    rewardItemStatisticService.processStatisticToday();
    return ResponseEntity.ok(true);
  }

  @PostMapping("reward/process-quantity")
  public ResponseEntity<?> processRewardSchedule() {
    rewardScheduleService.addRewardSegmentQuantity();
    return ResponseEntity.ok(true);
  }

  @PostMapping("statistic-user/current")
  public ResponseEntity<?> updateStatisticUserToday() {
    statisticUserService.updateStatisticUserToday();
    return ResponseEntity.ok(true);
  }

  @PostMapping("statistic-user/pre")
  public ResponseEntity<?> updateStatisticUserPre() {
    statisticUserService.updateStatisticUserPreDay();
    return ResponseEntity.ok(true);
  }

  @PostMapping("statistic-sub/current")
  public ResponseEntity<?> updateStatisticSubToday() {
    statisticSubService.updateStatisticSubToday();
    return ResponseEntity.ok(true);
  }

  @PostMapping("statistic-sub/pre")
  public ResponseEntity<?> updateStatisticSubPre() {
    statisticSubService.updateStatisticSubPreDay();
    return ResponseEntity.ok(true);
  }

  @PostMapping("statistic-revenue/current")
  public ResponseEntity<?> updateStatisticRevenueToday() {
    statisticRevenueService.updateStatisticRevenueToday();
    return ResponseEntity.ok(true);
  }

  @PostMapping("statistic-revenue/pre")
  public ResponseEntity<?> updateStatisticRevenuePre() {
    statisticRevenueService.updateStatisticRevenuePreDay();
    return ResponseEntity.ok(true);
  }
}
