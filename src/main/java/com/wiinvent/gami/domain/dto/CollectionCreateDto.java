package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.CollectionType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CollectionCreateDto extends CollectionUpdateDto{
  @NotNull
  private CollectionType type;
}
