package com.wiinvent.gami.domain.dto.gvc;

import com.wiinvent.gami.domain.entities.type.Status;
import lombok.Data;

@Data
public class GvcPackageUpdateDto{
  private String code;

  private Integer coin;

  private Integer point;

  private Status status;
}