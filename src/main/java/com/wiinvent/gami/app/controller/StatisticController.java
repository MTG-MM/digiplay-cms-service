package com.wiinvent.gami.app.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wiinvent.gami.domain.service.reward.RewardItemStatisticService;
import com.wiinvent.gami.domain.service.statistic.StatisticRevenueService;
import com.wiinvent.gami.domain.service.statistic.StatisticSubService;
import com.wiinvent.gami.domain.service.statistic.StatisticUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("v1/portal/statistic")
public class StatisticController {
  @Autowired
  private RewardItemStatisticService rewardItemStatisticService;

  @Autowired
  private StatisticSubService statisticSubService;

  @Autowired
  private StatisticUserService statisticUserService;

  @Autowired
  private StatisticRevenueService statisticRevenueService;

  @GetMapping("user")
  public ResponseEntity<?> getStatisticUser(
      @RequestParam String startDate,
      @RequestParam String endDate
  ) {
    return ResponseEntity.ok(statisticUserService.getStatisticUser(startDate, endDate));
  }

  @GetMapping("sub")
  public ResponseEntity<?> getStatisticSub(
      @RequestParam String startDate,
      @RequestParam String endDate
  ) {
    return ResponseEntity.ok(statisticSubService.getStatisticSub(startDate, endDate));
  }

  @GetMapping("revenue")
  public ResponseEntity<?> getStatisticRevenue(
      @RequestParam String startDate,
      @RequestParam String endDate
  ) {
    return ResponseEntity.ok(statisticRevenueService.getStatisticRevenue(startDate, endDate));
  }

  @GetMapping("rw-item")
  public ResponseEntity<?> statisticRewardItem(
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate gte,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate lte
  ) {
    return ResponseEntity.ok(rewardItemStatisticService.statisticTotal(gte, lte));
  }
}
