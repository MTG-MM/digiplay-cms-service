package com.wiinvent.gami.domain.response;

import lombok.Data;

@Data
public class ChallengeResponse {
  private Integer id;
  private Integer gameId;
  private String name;
  private String imageUrl;
  private String status;
  private String thumbUrl;
  private Long createdAt;
}
