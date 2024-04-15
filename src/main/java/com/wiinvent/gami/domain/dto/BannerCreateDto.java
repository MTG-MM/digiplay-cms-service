package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.BannerType;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BannerCreateDto {
  @NotNull(message = "Không được để trống")
  private BannerType type;

  @NotNull(message = "Không được để trống")
  private String imageUrl;

  private String linkTo;

  private String linkTracking;

  private Status status;
}