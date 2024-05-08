package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserSegmentRewardItems;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UserSegmentUpdateDto {
  private String name;
  private Boolean isDefault;
  @NotNull(message = "Không được để trống")
  private Long minPriority;
  @NotNull(message = "Không được để trống")
  private Long maxPriority;

  @NotNull(message = "Không được để trống")
  private Integer level;
  private List<UserSegmentRewardItems> rewardItems;

  @NotNull(message = "Không được để trống")
  private Integer requireExp;
  private Integer pointLimit;
  private Double pointBonusRate;
  private Integer subBonusRate;

  @NotNull(message = "Không được để trống")
  private Status status;
  private Integer extendPoint;
}
