package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.service.reward.RewardItemStatisticService;
import com.wiinvent.gami.domain.service.statistic.StatisticRevenueService;
import com.wiinvent.gami.domain.service.statistic.StatisticSubService;
import com.wiinvent.gami.domain.service.statistic.StatisticUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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

  @GetMapping(value = "user/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  public ResponseEntity<byte[]> getUserReport(
      @RequestParam String startDate,
      @RequestParam String endDate) throws IOException {
    byte[] excelFile = statisticUserService.getUserStatisticReport(startDate, endDate);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    headers.setContentDisposition(ContentDisposition.builder("attachment").filename("user_report.xlsx").build());
    return ResponseEntity.ok()
        .headers(headers)
        .body(excelFile);
  }

  @GetMapping("sub")
  public ResponseEntity<?> getStatisticSub(
      @RequestParam String startDate,
      @RequestParam String endDate
  ) {
    return ResponseEntity.ok(statisticSubService.getStatisticSub(startDate, endDate));
  }

  @GetMapping(value = "sub/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  public ResponseEntity<byte[]> getSubReport(
      @RequestParam String startDate,
      @RequestParam String endDate) throws IOException {
    byte[] excelFile = statisticSubService.getSubStatisticReport(startDate, endDate);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    headers.setContentDisposition(ContentDisposition.builder("attachment").filename("sub_report.xlsx").build());
    return ResponseEntity.ok()
        .headers(headers)
        .body(excelFile);
  }

  @GetMapping("revenue")
  public ResponseEntity<?> getStatisticRevenue(
      @RequestParam String startDate,
      @RequestParam String endDate
  ) {
    return ResponseEntity.ok(statisticRevenueService.getStatisticRevenue(startDate, endDate));
  }

  @GetMapping(value = "revenue/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  public ResponseEntity<byte[]> getRevenueReport(
      @RequestParam String startDate,
      @RequestParam String endDate) throws IOException {
    byte[] excelFile = statisticRevenueService.getRevenueStatisticReport(startDate, endDate);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    headers.setContentDisposition(ContentDisposition.builder("attachment").filename("revenue_report.xlsx").build());
    return ResponseEntity.ok()
        .headers(headers)
        .body(excelFile);
  }

  @GetMapping("rw-item")
  public ResponseEntity<?> statisticRewardItem(
      @RequestParam String startDate,
      @RequestParam String endDate
  ) {
    return ResponseEntity.ok(rewardItemStatisticService.statisticTotal(startDate, endDate));
  }
}
