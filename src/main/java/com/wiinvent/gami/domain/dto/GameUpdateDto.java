package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.GameStatus;
import lombok.Data;

@Data
public class GameUpdateDto{

  private Integer categoryId;

  private String name;

  private String imageUrl;

  private String secretKey;

  private String thumbUrl;

  private String banner;

  private String description;

  private Integer priority;

  private String navLink;

  private String apiVerifyAccount;

  private String bodyApiVerifyAccount;

  private String apiPayment;

  private String bodyApiPayment;

  private Integer gameTypeId;

  private GameStatus status;

  private Boolean isHot;

  private Boolean isNew;

  private Boolean isUpdate;

  private Boolean isLock;

  private Integer levelUnlock;
}
