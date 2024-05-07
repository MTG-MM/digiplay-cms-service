package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.ProductType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.PaymentMethodInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PackageCreateDto{
  @NotEmpty(message = "Không được để trống")
  private String name;

  @NotNull(message = "Không được để trống")
  private String code;

  private Integer point;

  private Integer coin;

  @Schema(description = "thời gian sử dụng của gói")
  private Integer daySub;

  private Status status;

  private Integer coinDaily;

  private String externalImageId;

  @NotNull(message = "Không được để trống")
  private Integer packageTypeId;

  private Integer pointDaily;

  private List<PaymentMethodInfo> paymentMethodInfo;

  private Long startTime;

  private Long endTime;

  private Integer pointBonus;

  private Integer coinBonus;

  private String description;

  @NotNull(message = "Không được để trống")
  private ProductType type;
}
