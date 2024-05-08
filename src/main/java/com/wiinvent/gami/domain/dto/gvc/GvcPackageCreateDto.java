package com.wiinvent.gami.domain.dto.gvc;

import com.wiinvent.gami.domain.entities.type.Status;
import lombok.Data;

@Data
public class GvcPackageCreateDto extends GvcPackageUpdateDto{
  private Integer gameId;
}