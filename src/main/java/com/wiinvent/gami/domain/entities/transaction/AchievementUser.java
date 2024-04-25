package com.wiinvent.gami.domain.entities.transaction;

import com.wiinvent.gami.domain.entities.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;
@Getter
@Setter
@Entity
@Table(name = "achievement_user")
public class AchievementUser extends BaseEntity {
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotNull
  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @NotNull
  @Column(name = "achievement_id", nullable = false)
  private Integer achievementId;

  @ColumnDefault("0")
  @Column(name = "score")
  private Double score = 0D;
}
