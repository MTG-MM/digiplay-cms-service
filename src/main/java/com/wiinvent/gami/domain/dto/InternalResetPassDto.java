package com.wiinvent.gami.domain.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class InternalResetPassDto {
  private UUID userId;
}
