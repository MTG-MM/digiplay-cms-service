package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.CollectionType;
import com.wiinvent.gami.domain.entities.type.Status;
import lombok.Data;

@Data
public class CollectionResponse {
  private Long id;
  private String name;
  private String description;
  private String imageUrl;
  private CollectionType type;
  private Status status;
  private Long externalId;
  private Long pieceNumber;
  private Long luckyPoint;
}
