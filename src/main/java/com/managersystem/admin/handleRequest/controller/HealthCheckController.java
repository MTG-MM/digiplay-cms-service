package com.managersystem.admin.handleRequest.controller;

import com.managersystem.admin.server.service.RewardScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class HealthCheckController {

  @Autowired
  RewardScheduleService rewardScheduleService;
  @GetMapping("")
  public ResponseEntity<?> healthCheck(){
    return ResponseEntity.ok("Kết nối thành công");
  }

  @PostMapping("init-data-spin-test")
  public ResponseEntity<?> initDataSpinTest(){
    return ResponseEntity.ok(rewardScheduleService.initData());
  }
}
