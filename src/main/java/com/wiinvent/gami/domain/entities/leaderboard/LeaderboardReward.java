package com.wiinvent.gami.domain.entities.leaderboard;


import com.wiinvent.gami.domain.entities.BaseEntity;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "leaderboard_reward")
public class LeaderboardReward extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "rank")
  private Integer rank;

  @Column(name = "reward_items")
  @JdbcTypeCode(SqlTypes.JSON)
  private List<UserRewardItems> rewardItems;

  @Size(max = 50)
  @Column(name = "code", length = 50)
  private String code;

}