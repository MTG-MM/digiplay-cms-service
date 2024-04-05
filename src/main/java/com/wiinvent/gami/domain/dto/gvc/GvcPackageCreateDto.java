package com.wiinvent.gami.domain.dto.gvc;

import com.wiinvent.gami.domain.entities.type.Status;
import lombok.Data;

@Data
public class GvcPackageCreateDto {
  private Integer gameId;

  private String code;

  private Integer gvc1Amount;

  private Integer gvc2Amount;

  private Status status;
}