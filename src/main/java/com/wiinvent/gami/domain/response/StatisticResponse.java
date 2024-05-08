package com.wiinvent.gami.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data

public class StatisticResponse {
  private Long rewardItemId;
  private String rewardItemName;
  private Long rewardSegmentId;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate daily;
  private Integer totalRewardReceived;
  private Integer totalUser;
  private Integer totalRewardRemain;
}
