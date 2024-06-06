package com.wiinvent.gami.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wiinvent.gami.domain.entities.type.RequestGamiType;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;
@Data
public class InternalTaskRequestDto {
  private UUID userId;
  private Integer itemId;
  private Integer score;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate date;
  private RequestGamiType requestType;
}
