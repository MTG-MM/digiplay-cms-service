package com.wiinvent.gami.app.controller.response;

import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.entities.type.StoreType;
import lombok.Data;

@Data
public class RewardItemStoreResponse {

  private Long id;

  private String name;

  private StoreType type;

  private Status status;
}
