package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.GameStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class GameUpdateDto{

  private Integer categoryId;

  private String name;

  @Schema(description = "url icon game")
  private String imageUrl;

  private String secretKey;

  @Schema(description = "url thumb game")
  private String thumbUrl;

  @Schema(description = "url thumb challenge game")
  private String thumbChallengeUrl;

  private String banner;

  private String description;

  private Integer priority;

  private String navLink;

  private String apiVerifyAccount;

  private String bodyApiVerifyAccount;

  private String apiPayment;

  private String bodyApiPayment;

  private List<Integer> gameTypeId;

  private GameStatus status;

  private Boolean isHot;

  private Boolean isNew;

  private Boolean isUpdate;

  private Boolean isLock;

  private Integer levelUnlock;
}
