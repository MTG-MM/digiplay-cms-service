package com.wiinvent.gami.domain.entities.game;

import com.wiinvent.gami.domain.entities.BaseEntity;
import com.wiinvent.gami.domain.entities.reward.RewardItemInfo;
import com.wiinvent.gami.domain.utils.Converter.RewardItemInfoConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "game_tournament_reward")
public class GameTournamentReward extends BaseEntity {
  @Id
  @Size(max = 16)
  @Column(name = "id", nullable = false, length = 16)
  private String id;

  @Size(max = 50)
  @Column(name = "tournament_event_id", length = 50)
  private String tournamentEventId;

  @Size(max = 16)
  @Column(name = "user_id", length = 16)
  private String userId;

  @Column(name = "reward_item_id")
  private Integer rewardItemId;

  @Column(name = "user_rank")
  private Integer userRank;

  @Size(max = 2000)
  @Column(name = "reward_item_info", length = 2000)
  @Convert(converter = RewardItemInfoConverter.class)
  private RewardItemInfo rewardItemInfo;
}