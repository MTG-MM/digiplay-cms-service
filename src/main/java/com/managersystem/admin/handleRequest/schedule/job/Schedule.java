package com.managersystem.admin.handleRequest.schedule.job;

import com.managersystem.admin.server.service.RewardScheduleService;
import com.managersystem.admin.server.utils.DateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class Schedule {

  @Autowired private RewardScheduleService rewardScheduleService;
  @Scheduled(fixedDelay = 1000 * 60)
  public void processRewardSchedule() {
    long start = DateUtils.getNowMillisAtUtc();
    log.debug("===============>processRewardSchedule: {} ", DateUtils.getNowDateTimeAtVn());
    rewardScheduleService.addRewardSegmentQuantity();
    log.debug("===============>processRewardSchedule: duration{} ", DateUtils.getNowMillisAtUtc() - start);
  }
}
