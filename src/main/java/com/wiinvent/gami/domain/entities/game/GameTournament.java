package com.wiinvent.gami.domain.entities.game;

import com.wiinvent.gami.domain.entities.BaseEntity;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "game_tournament")
@NoArgsConstructor
@AllArgsConstructor
public class GameTournament extends BaseEntity {
  @Id
  @Column(name = "id", nullable = false)
  private String id;

  @Column(name = "game_id")
  private Integer gameId;

  @Column(name = "name")
  private String name;

  @Column(name = "start_at")
  private Long startAt;

  @Column(name = "end_at")
  private Long endAt;

  @Column(name = "duration")
  private Integer duration;

  @Column(name = "status")
  private Status status;
}
