package com.managersystem.admin.handleRequest.controller.response;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class IndustryGroupResponse {
  private int id;

  private String industryGroupCode;

  private String industryGroupName;

  private String imageUrl;

  private String navLinkUrl;

  private String description;

  private Long totalSeller = 0L;

  private Long totalPurchase = 0L;

  private Long totalView = 0L;
}
