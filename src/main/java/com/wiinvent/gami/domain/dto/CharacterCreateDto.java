package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.CharacterCategoryType;
import lombok.Data;

@Data
public class CharacterCreateDto extends CharacterUpdateDto{
  private CharacterCategoryType categoryType;
  private String type;
}
