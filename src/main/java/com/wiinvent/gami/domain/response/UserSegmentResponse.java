package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserSegmentRewardItems;
import lombok.Data;

import java.util.List;

@Data
public class UserSegmentResponse {
  private Long id;
  private String name;
  private Boolean isDefault;
  private Long minPriority;
  private Long maxPriority;
  private Integer level;
  private List<UserSegmentRewardItems> rewardItems;
  private Integer requireExp;
  private Integer pointLimit;
  private Double pointBonusRate;
  private Integer subBonusRate;
  private Status status;
  private Integer extendPoint;
}
