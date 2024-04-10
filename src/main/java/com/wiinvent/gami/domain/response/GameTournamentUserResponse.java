package com.wiinvent.gami.domain.response;

import lombok.Data;

import java.util.UUID;

@Data
public class GameTournamentUserResponse {
  private UUID userId;
  private String firstName;
  private Integer coin;
  private Double point;
  private Long createdAt;
}
