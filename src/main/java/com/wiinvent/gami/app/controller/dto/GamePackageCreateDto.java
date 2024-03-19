package com.wiinvent.gami.app.controller.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GamePackageCreateDto extends GamePackageUpdateDto{

  Integer gameId;
}
