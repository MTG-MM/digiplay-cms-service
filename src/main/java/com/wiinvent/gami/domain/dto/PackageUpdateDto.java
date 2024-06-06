package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.PaymentMethodInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PackageUpdateDto {
  @NotEmpty(message = "Không được để trống")
  private String name;

  @NotNull(message = "Không được để trống")
  private String code;

  @NotNull(message = "Không được để trống")
  @Min(0)
  private Integer point;

  @NotNull(message = "Không được để trống")
  @Min(0)
  private Integer coin;

  @NotNull(message = "Không được để trống")
  @Min(0)
  private Integer ticket;

  @Schema(description = "thời gian sử dụng của gói")
  @Min(0)
  private Integer daySub;

  @NotNull(message = "Không được để trống")
  private Status status;

  @NotNull(message = "Không được để trống")
  @Min(0)
  private Integer coinDaily;

  @NotNull(message = "Không được để trống")
  @Min(0)
  private Integer pointDaily;

  private List<PaymentMethodInfo> paymentMethodInfo;

  private Long startTime;

  private Long endTime;

  @Min(0)
  private Integer pointBonus = 0;
  @Min(0)
  private Integer coinBonus = 0;
  @Min(0)
  private Integer secondBonusPoint = 0;
  @Min(0)
  private Integer secondBonusCoin = 0;

  private String description;

  private String imageUrl;
}
