package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChallengeUpdateDto {
  private String name;
  private String imageUrl;
  private String thumbUrl;
  @NotNull
  private Status status;
}
