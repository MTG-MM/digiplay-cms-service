package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.GameStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class GameUpdateDto{
  @NotNull(message = "Không được để trống")
  private Integer categoryId;

  @NotNull(message = "Không được để trống")
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

  @NotNull(message = "Không được để trống")
  private String navLink;

  @NotNull(message = "Không được để trống")
  private String apiVerifyAccount;

  private String bodyApiVerifyAccount;

  @NotNull(message = "Không được để trống")
  private String apiPayment;

  private String bodyApiPayment;

  private List<Integer> gameTypeId;

  @NotNull(message = "Không được để trống")
  private GameStatus status;

  private Boolean isHot = false;

  private Boolean isNew = false;

  private Boolean isUpdate = false;

  private Boolean isLock = false;

  @NotNull
  private Integer levelUnlock;

  private String linkAds;
}
