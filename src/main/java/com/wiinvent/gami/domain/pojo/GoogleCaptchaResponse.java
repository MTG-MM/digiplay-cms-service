package com.wiinvent.gami.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class GoogleCaptchaResponse {
  private Boolean success;
  @JsonProperty("challenge_ts")
  private Timestamp challengeTs;
  private String hostname;
  private Float score;
  @JsonProperty("error-codes")
  private List<String> errorCodes;
}
