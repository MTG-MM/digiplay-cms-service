package com.wiinvent.gami.domain.entities.reward;

import com.wiinvent.gami.domain.entities.type.RewardItemType;
import lombok.Data;

@Data
public class RewardItemInfo {
  private Integer id;
  private String name;
  private String description;
  private RewardItemType type;
  private Integer amount;
  private String code;
  private String codeDetailId;
}
