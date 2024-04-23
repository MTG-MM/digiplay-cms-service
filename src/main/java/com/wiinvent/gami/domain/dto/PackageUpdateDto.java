package com.wiinvent.gami.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PackageUpdateDto extends PackageCreateDto{
  @NotNull(message = "Không được để trống")
  private Integer id;
}
