package com.wiinvent.gami.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GamePackageCreateDto extends GamePackageUpdateDto{

  Integer gameId;
}
