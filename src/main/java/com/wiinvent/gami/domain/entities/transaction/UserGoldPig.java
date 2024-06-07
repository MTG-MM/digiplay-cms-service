package com.wiinvent.gami.domain.entities.transaction;

import com.wiinvent.gami.domain.entities.BaseEntity;
import com.wiinvent.gami.domain.entities.type.QualifyRewardStatus;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.service.BaseService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user_gold_pig")
public class UserGoldPig extends BaseEntity {
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "reward_at")
  private Long rewardAt;

  @Column(name = "accumulate_point")
  private Integer accumulatePoint;

  @Lob
  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private QualifyRewardStatus status;

  @NotNull
  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "price")
  private Integer price;

}