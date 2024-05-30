package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.pojo.PaymentMethodInfo;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GamePackageUpdateDto {
  @NotNull(message = "Không được để trống")
  private Integer id;

  @NotNull(message = "Không được để trống")
  @Size(max = 50)
  @NotEmpty
  @NotBlank(message = "Không được để trống")
  private String code;

  @NotNull
  @Min(0)
  private Integer point;

  @NotNull
  @Min(0)
  private Integer coin;

  @NotNull
  @Min(0)
  private Integer price;

  @Size(max = 1000)
  private String imageUrl;

  @Size(max = 1000)
  private String thumbUrl;

  @Size(max = 1000)
  private String description;

  @NotNull
  private Status status;

  @Min(0)
  private Integer priority;

  List<PaymentMethodInfo> paymentMethodInfo = new ArrayList<>();
}