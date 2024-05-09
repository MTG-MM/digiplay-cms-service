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
public class PackageUpdateDto {
  @NotEmpty(message = "Không được để trống")
  private String name;

  @NotNull(message = "Không được để trống")
  private String code;

  @NotNull(message = "Không được để trống")
  private Integer point;

  @NotNull(message = "Không được để trống")
  private Integer coin;

  @NotNull
  private Integer ticket;

  @Schema(description = "thời gian sử dụng của gói")
  private Integer daySub;

  @NotNull(message = "Không được để trống")
  private Status status;

  @NotNull(message = "Không được để trống")
  private Integer coinDaily;

  @NotNull(message = "Không được để trống")
  private Integer pointDaily;

  private List<PaymentMethodInfo> paymentMethodInfo;

  private Long startTime;

  private Long endTime;

  private Integer pointBonus;

  private Integer coinBonus;

  private String description;

  @NotNull(message = "Không được để trống")
  private ProductType type;

  private String imageUrl;
}
