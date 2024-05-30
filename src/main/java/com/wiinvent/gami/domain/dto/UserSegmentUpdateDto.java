package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserSegmentRewardItems;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserSegmentUpdateDto {
  private String name;
  private Boolean isDefault;
  @NotNull(message = "Không được để trống")
  @Min(0)
  private Long minPriority;
  @NotNull(message = "Không được để trống")
  @Min(0)
  private Long maxPriority;

  @NotNull(message = "Không được để trống")
  @Min(1)
  private Integer level;
  private List<UserSegmentRewardItems> rewardItems = new ArrayList<>();

  @NotNull(message = "Không được để trống")
  @Min(0)
  private Integer requireExp;
  @Min(0)
  private Integer pointLimit;
  @Min(0)
  private Double pointBonusRate;
  @Min(0)
  private Integer subBonusRate;

  @NotNull(message = "Không được để trống")
  private Status status;
  @Min(0)
  private Integer extendPoint;
}
