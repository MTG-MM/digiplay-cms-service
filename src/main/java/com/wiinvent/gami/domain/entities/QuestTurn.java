package com.wiinvent.gami.domain.entities;

import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.entities.type.UserType;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import com.wiinvent.gami.domain.utils.Converter.UserRewardItemConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "quest_turn")
public class QuestTurn extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Size(max = 100)
  @Column(name = "name", length = 100)
  private String name;

  @Column(name = "quest_number")
  private Integer questNumber;

  @Column(name = "turn")
  private Integer turn;

  @Column(name = "point_price")
  private Integer pointPrice;

  @Column(name = "coin_price")
  private Integer coinPrice;

  @Column(name = "quest_cd")
  private Integer questCd;

  @Column(name = "view_ads_skip_duration")
  private Integer viewAdsSkipDuration;

  @Column(name = "coin_skip_cd_price")
  private Integer coinSkipCdPrice;

  @Column(name = "duration")
  private Integer duration;

  @Column(name = "duration_bonus")
  private Integer durationBonus;

  @Column(name = "duration_bonus_price")
  private Integer durationBonusPrice;

  @Lob
  @Column(name = "status")
  private Status status;

  @Column(name = "active_for_user")
  @JdbcTypeCode(SqlTypes.JSON)
  private List<UserType> activeForUser;

  @Column(name = "free_for_user")
  @JdbcTypeCode(SqlTypes.JSON)
  private List<UserType> freeForUser;

  @Column(name = "reward_items")
  @Convert(converter = UserRewardItemConverter.class)
  private List<UserRewardItems> rewardItems;

}