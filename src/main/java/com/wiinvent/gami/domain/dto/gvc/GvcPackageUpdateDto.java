package com.wiinvent.gami.domain.dto.gvc;

import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GvcPackageUpdateDto{
  private String code;

  @NotNull
  private Integer coin;

  @NotNull
  private Integer point;

  private Status status;
}