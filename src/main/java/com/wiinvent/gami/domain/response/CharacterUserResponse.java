package com.wiinvent.gami.domain.response;

import lombok.Data;

import java.util.UUID;

@Data
public class CharacterUserResponse {
  private UUID id;
  private Integer characterId;
  private String characterName;
  private Integer quantity;
  private Long expireTime;
  private Long createdAt;
}
