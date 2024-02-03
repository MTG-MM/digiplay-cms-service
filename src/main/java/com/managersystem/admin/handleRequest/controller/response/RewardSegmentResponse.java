package com.managersystem.admin.handleRequest.controller.response;

import com.managersystem.admin.server.entities.type.RewardType;
import com.managersystem.admin.server.entities.type.Status;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class RewardSegmentResponse {

  private Long id;

  private String name;

  private String code;

  private String imageUrl;

  private Status status;
}
