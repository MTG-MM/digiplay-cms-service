package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.CollectionType;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CollectionUpdateDto {
  private String name;
  private String description;
  private String imageUrl;
  private Long pieceNumber;
  @NotNull
  private Status status;
  @NotNull
  private Long luckyPoint;
}
