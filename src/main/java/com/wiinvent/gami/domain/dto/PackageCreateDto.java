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
public class PackageCreateDto extends PackageUpdateDto{
  @NotNull(message = "Không được để trống")
  private Integer packageTypeId;
}
