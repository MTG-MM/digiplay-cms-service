package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.Status;
import lombok.Data;

@Data
public class GvcPackageResponse {
  private Integer id;

  private Integer gameId;

  private String gameName;

  private String code;

  private Integer point;

  private Integer coin;

  private Status status;

  private Long createdAt;

  private Long updatedAt;
}