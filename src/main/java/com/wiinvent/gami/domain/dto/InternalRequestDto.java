package com.wiinvent.gami.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class InternalRequestDto {
  @NotNull
  private UUID userId;
  @NotNull
  @Min(0)
  private Double amount;
  private Long itemId;
  private Integer score;
  private String note;

  private RequestGamiType requestType;

  public enum RequestGamiType {
    ADD,
    SUBTRACT
  }
}
