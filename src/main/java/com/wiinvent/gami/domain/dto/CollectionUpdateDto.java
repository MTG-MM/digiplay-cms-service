package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CollectionUpdateDto {
  private String name;
  private String description;
  private String imageUrl;
  private Long pieceNumber = 0L;
  @NotNull
  private Status status;
  @NotNull
  private Long luckyPoint;
//  @NotNull
//  @Size(max = 1, min = 1)
  private List<UserRewardItems> rewardItems;
  @NotNull
  private List<UserRewardItems> collectionPiece;

}
