package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.CollectionType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CollectionCreateDto extends CollectionUpdateDto{
  @NotNull
  private Long externalId;
  @NotNull
  private CollectionType type;
}
