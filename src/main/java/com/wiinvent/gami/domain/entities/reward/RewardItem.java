package com.wiinvent.gami.domain.entities.reward;

import com.wiinvent.gami.domain.entities.type.RewardItemType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.utils.DateUtils;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "reward_item")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RewardItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "reward_name")
  private String rewardName;

  @Column(name = "value")
  private Long value;

  @Column(name = "description")
  private String description;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "reward_type")
  @Enumerated(EnumType.STRING)
  private RewardItemType rewardItemType;

  @Column(name = "reward_type_id")
  private Long rewardTypeId;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private Status status;

  @Column(name = "is_limited", columnDefinition = "BIT")
  private Boolean isLimited;

  @Column(name = "remaining_quantity")
  private Long quantity = 0L;

  @Column(name = "total_quantity")
  private Long totalQuantity = 0L;

  @Column(name = "used_quantity")
  private Long usedQuantity;

  @Column(name = "external_id")
  private String externalId;

  @Column(name = "created_at")
  private Long createdAt = DateUtils.getNowMillisAtUtc();

  @Column(name = "updated_at")
  private Long updatedAt = DateUtils.getNowMillisAtUtc();

  public void addQuantity(long amount) {
    this.quantity = this.getQuantity() + amount;
  }

  public void minusQuantity(long amount) {
    this.quantity = this.getQuantity() - amount;
  }
}
