package com.wiinvent.gami.domain.entities.user;

import com.wiinvent.gami.domain.entities.BaseEntity;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.utils.Converter.UserSegmentRewardItemConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Table( name = "user_segment")
@Entity
public class UserSegment extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "is_default", columnDefinition = "BIT")
  private Boolean isDefault;

  @Column(name = "min_priority")
  private Long minPriority;

  @Column(name = "max_priority")
  private Long maxPriority;

  @Column(name = "level")
  private Integer level;

  @Column(name = "reward_items")
  @Convert(converter = UserSegmentRewardItemConverter.class)
  private List<UserSegmentRewardItems> rewardItems;

  @Column(name = "require_exp")
  private Integer requireExp;

  @Column(name = "point_limit")
  private Integer pointLimit;

  @Column(name = "point_bonus_rate")
  private Double pointBonusRate;

  @Column(name = "sub_bonus_rate")
  private Integer subBonusRate;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private Status status;
}
