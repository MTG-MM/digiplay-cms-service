package com.managersystem.admin.handleRequest.controller.dto;

import com.managersystem.admin.server.entities.type.RewardType;
import com.managersystem.admin.server.entities.type.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardItemDto {

  @NotNull(message = "Không được để trống")
  @NotBlank(message = "Không được để trống")
  private String rewardName;

  private String description;

  private String imageUrl;

  @NotNull
  private RewardType rewardType;

  @NotNull
  private Status status;

  @NotNull
  private Boolean isLimited;

  private String externalId;
}
