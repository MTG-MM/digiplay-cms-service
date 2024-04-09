package com.wiinvent.gami.domain.entities.user;

import com.wiinvent.gami.domain.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "game_tournament_user")
public class GameTournamentUser extends BaseEntity {
  @Id
  @Size(max = 80)
  @Column(name = "id", nullable = false, length = 80)
  private String id;

  @Size(max = 16)
  @NotNull
  @Column(name = "user_id", nullable = false, length = 16)
  private String userId;

  @Size(max = 50)
  @Column(name = "tournament_event_id", length = 50)
  private String tournamentEventId;

  @Column(name = "coin")
  private Integer coin;

  @Column(name = "point")
  private Double point;

}