package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.CollectionType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import lombok.Data;

import java.util.List;

@Data
public class CollectionResponse {
  private Long id;
  private String name;
  private String description;
  private String imageUrl;
  private CollectionType type;
  private Status status;
  private List<UserRewardItems> rewardItems;
  private Long pieceNumber;
  private Long luckyPoint;
}
