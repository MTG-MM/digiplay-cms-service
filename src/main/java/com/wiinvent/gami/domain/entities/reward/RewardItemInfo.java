package com.wiinvent.gami.domain.entities.reward;

import com.wiinvent.gami.domain.entities.type.RewardType;
import lombok.Data;

@Data
public class RewardItemInfo {
  private Integer id;
  private String name;
  private String description;
  private RewardType type;
  private Integer amount;
  private String code;
  private String codeDetailId;
}
