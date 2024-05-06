package com.wiinvent.gami.app.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wiinvent.gami.domain.service.reward.RewardItemStatisticService;
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

  @GetMapping("report")
  public ResponseEntity<?> statisticReport(
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate gte,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate lte
  ) {
    return ResponseEntity.ok(rewardItemStatisticService.statisticTotal(gte, lte));
  }
}
