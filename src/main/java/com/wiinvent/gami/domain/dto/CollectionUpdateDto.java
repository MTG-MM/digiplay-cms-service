package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.Status;
import lombok.Data;

@Data
public class CollectionUpdateDto {
  private String name;
  private String description;
  private String imageUrl;
  private Status status;
  private Long luckyPoint;
}
