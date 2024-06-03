package com.wiinvent.gami.domain.response;

import lombok.Data;

@Data
public class CharacterUserResponse {
  private Integer characterId;
  private String characterName;
  private Integer quantity;
  private Long expireTime;
  private Long createdAt;
}
