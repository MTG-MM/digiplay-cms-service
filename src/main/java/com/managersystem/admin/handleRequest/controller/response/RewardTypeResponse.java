package com.managersystem.admin.handleRequest.controller.response;

import com.managersystem.admin.server.entities.type.Status;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class RewardTypeResponse {

  private Long id;

  private String name;

  private String description;

  private String type;

  private Status status;

}
