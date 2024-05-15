package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.CollectionType;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CollectionCreateDto extends CollectionUpdateDto{
  @NotNull
  @Size(max = 1, min = 1)
  private List<UserRewardItems> rewardItems;
  @NotNull
  private CollectionType type;
}
