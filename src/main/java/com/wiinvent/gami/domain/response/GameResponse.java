package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.game.GameStar;
import com.wiinvent.gami.domain.entities.type.GameStatus;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class GameResponse {
  private Integer id;
  private String name;
  private Integer categoryId;
  private UUID teamId;
  private String secretKey;
  private Integer priority;
  private String imageUrl;
  private String thumbUrl;
  private String thumbChallengeUrl;
  private String banner;
  private String navLink;
  private String apiVerifyAccount;
  private String bodyApiVerifyAccount;
  private String apiPayment;
  private String bodyApiPayment;
  private List<GameTypeResponse> gameType;
  private String description;
  private GameStatus status;
  private Boolean isHot;
  private Boolean isNew;
  private Boolean isUpdate;
  private Boolean isLock;
  private Integer levelUnlock;
  private GameStar gameStar;
  private Long createdAt;
  private Long updatedAt;
}
