package com.wiinvent.gami.domain.entities.game;

import com.wiinvent.gami.domain.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "game_tournament_event")
public class GameTournamentEvent extends BaseEntity {
  @Id
  @Size(max = 50)
  @Column(name = "id", nullable = false, length = 50)
  private String id;

  @NotNull
  @Column(name = "game_tournament_id", nullable = false)
  private Integer gameTournamentId;

  @Column(name = "start_time", length = 20)
  private LocalDateTime startTime;

  @Column(name = "end_time", length = 20)
  private LocalDateTime endTime;

  @Column(name = "is_reward")
  private Boolean isReward;

}