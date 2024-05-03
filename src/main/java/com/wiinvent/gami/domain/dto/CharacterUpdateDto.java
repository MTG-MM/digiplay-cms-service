package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.CharacterGenderType;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CharacterUpdateDto {
  private String name;
  @NotNull
  private Status status;
  @NotNull
  private Integer coinPrice;
  @NotNull
  private Integer pointPrice;
  private String externalId;
  private CharacterGenderType gender;
  private Boolean isDefault;
  private Boolean isSell;
  private Boolean isHot;
  private Boolean isTrend;
  private Boolean isNew;
  private Boolean isEvent;
}
