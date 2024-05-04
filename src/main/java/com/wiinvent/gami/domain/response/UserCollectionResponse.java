package com.wiinvent.gami.domain.response;

import lombok.Data;

@Data
public class UserCollectionResponse {
  private Long collectionId;
  private String collectionName;
  private Integer quantity;
  private Long createdAt;
}
