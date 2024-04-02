package com.wiinvent.gami.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UploadResponse {
  @JsonProperty("img_url")
  private String imgUrl;
}
