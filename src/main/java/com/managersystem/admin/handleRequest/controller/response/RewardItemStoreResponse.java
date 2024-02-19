package com.managersystem.admin.handleRequest.controller.response;

import com.managersystem.admin.server.entities.type.Status;
import com.managersystem.admin.server.entities.type.StoreType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class RewardItemStoreResponse {

  private Long id;

  private String name;

  private StoreType type;

  private Status status;
}
