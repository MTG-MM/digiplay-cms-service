package com.wiinvent.gami.domain.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class InternalQuestRequestDto {
  private Integer id;
  private UUID userId;
  private Integer score;
}
