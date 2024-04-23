package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.ResourceType;
import lombok.Data;

import java.util.UUID;

@Data
public class CharacterUserTransactionResponse {
  private UUID id;
  private Integer characterId;
  private String name;
  private Integer coinPrice;
  private Integer pointPrice;
  private ResourceType resourceType;
  private String note;
  private Long createdAt;
}
