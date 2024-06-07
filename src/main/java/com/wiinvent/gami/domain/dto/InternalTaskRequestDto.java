package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.RequestGamiType;
import lombok.Data;

import java.util.UUID;
@Data
public class InternalTaskRequestDto {
  private UUID userId;
  private Integer itemId;
  private Integer score;
  private RequestGamiType requestType;
}
