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
  private Boolean isDefault = false;
  private Boolean isSell = false;
  private Boolean isHot = false;
  private Boolean isTrend = false;
  private Boolean isNew = false;
  private Boolean isEvent = false;
  private Boolean isExpire = false;
  private Long duration;
}
