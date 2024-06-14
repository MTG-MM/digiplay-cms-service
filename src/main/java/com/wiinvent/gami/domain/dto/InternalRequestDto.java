package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.RequestGamiType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class InternalRequestDto {
  @NotNull
  private UUID userId;
  @NotNull
  @Min(1)
  private Double amount;
  private Long itemId;
  private String note;
  private RequestGamiType requestType;
}
