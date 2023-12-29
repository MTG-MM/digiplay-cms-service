package com.managersystem.admin.handleRequest.controller.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class IndustryGroupDto {

  private String industryGroupCode;

  private String industryGroupName;

  private String imageUrl;

  private String navLinkUrl;

  private String description;
}
