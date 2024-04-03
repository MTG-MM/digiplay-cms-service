package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.GameTypeStatus;
import lombok.Data;

@Data
public class GameTypeResponse {
  private Integer id;
  private String name;
  private String description;
  private GameTypeStatus status;
  private Long createdAt;
  private Long updatedAt;
}
