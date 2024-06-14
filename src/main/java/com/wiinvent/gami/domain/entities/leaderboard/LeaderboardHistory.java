package com.wiinvent.gami.domain.entities.leaderboard;


import com.wiinvent.gami.domain.entities.BaseEntity;
import com.wiinvent.gami.domain.entities.type.QualifyRewardStatus;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "leaderboard_history")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaderboardHistory extends BaseEntity {
  @Id
  @Column(name = "id", nullable = false, length = 16)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotNull
  @Column(name = "leaderboard_event_id", nullable = false)
  private Long leaderboardEventId;

  @NotNull
  @Column(name = "user_id", nullable = false, length = 16)
  private UUID userId;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", length = 50)
  private QualifyRewardStatus status;

  @Column(name = "reward_items")
  @JdbcTypeCode(SqlTypes.JSON)
  private List<UserRewardItems> rewardItems;

  @Column(name = "point")
  private Long point;

  public void addPoint(Long point) {
    this.point = this.point + point;
  }

  public void subtractPoint(long amount) {
    this.point = Math.max(this.point - amount, 0);
  }
}