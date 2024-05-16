package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.Collection;
import com.wiinvent.gami.domain.entities.type.Status;
import lombok.Data;

@Data
public class CollectionInTypeResponse {
  private Long id;
  private String rewardName;
  private String description;
  private String imageUrl;
  private Status status;
  private Long luckyPoint;

  public void toResponse(Collection collection) {
    id = collection.getId();
    rewardName = collection.getName();
    description = collection.getDescription();
    imageUrl = collection.getImageUrl();
    status = collection.getStatus();
    luckyPoint = collection.getLuckyPoint();
  }
}
