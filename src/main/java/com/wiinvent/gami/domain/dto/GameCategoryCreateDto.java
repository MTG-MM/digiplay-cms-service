package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GameCategoryCreateDto {
  @NotNull(message = "không được để trống")
  private String name;

  private Status status;

  private String categoryType;

  private Boolean isRequireSub;

  private Integer point;

  private Integer coin;
}
