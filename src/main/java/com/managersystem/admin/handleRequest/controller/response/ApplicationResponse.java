package com.managersystem.admin.handleRequest.controller.response;

import lombok.Data;

@Data
public class ApplicationResponse {
  private int id;

  private String applicationCode;

  private String applicationName;

  private String imageUrl;

  private String navLinkUrl;

  private String description;

  private Long totalSeller = 0L;

  private Long totalPurchase = 0L;

  private Long totalView = 0L;
}
