package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.Status;
import lombok.Data;

@Data
public class GameCategoryResponse {
  private Integer id;

  private String name;

  private Status status;

  private String categoryType;

  private Boolean isRequireSub;

  private Integer point;

  private Integer coin;

  private String description;

  private Long createdAt;

  private Long updatedAt;
}
