package com.wiinvent.gami.app.controller.dto;

import lombok.Data;

@Data
public class ApplicationDto {

  private String applicationCode;

  private String applicationName;

  private String imageUrl;

  private String navLinkUrl;

  private String description;
}
