package com.wiinvent.gami.app.controller.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class GameCreateDto extends GameUpdateDto {

  private UUID teamId;
}
