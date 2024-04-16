package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.Status;
import lombok.Data;

@Data
public class CharacterUpdateDto {
  private String name;
  private Status status;
  private Integer coinPrice;
  private Integer pointPrice;
  private String externalId;
  private Boolean isDefault;
  private Boolean isSell;
  private String gender;
}