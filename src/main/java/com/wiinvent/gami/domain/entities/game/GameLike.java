package com.wiinvent.gami.domain.entities.game;

import com.wiinvent.gami.domain.entities.BaseEntity;
import com.wiinvent.gami.domain.entities.type.LikeStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "game_like")
public class GameLike extends BaseEntity {
  @Id
  @Column(name = "id", nullable = false)
  private String id;

  @NotNull
  @Column(name = "game_id", nullable = false)
  private Integer gameId;

  @NotNull
  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "like_status", nullable = false)
  private LikeStatus likeStatus;

}