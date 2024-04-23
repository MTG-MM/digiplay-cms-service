package com.wiinvent.gami.domain.entities.game;

import com.wiinvent.gami.domain.entities.BaseEntity;
import com.wiinvent.gami.domain.pojo.RewardItemInfo;
import com.wiinvent.gami.domain.utils.Converter.RewardItemInfoConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "game_tournament_reward")
public class GameTournamentReward extends BaseEntity {
  @Id
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  @Column(name = "user_id", columnDefinition = "BINARY(16)")
  private UUID userId;

  @Size(max = 50)
  @Column(name = "tournament_event_id", length = 50)
  private String tournamentEventId;

  @Column(name = "reward_item_id")
  private Integer rewardItemId;

  @Column(name = "user_rank")
  private Integer userRank;

  @Size(max = 2000)
  @Column(name = "reward_item_info", length = 2000)
  @Convert(converter = RewardItemInfoConverter.class)
  private RewardItemInfo rewardItemInfo;
}