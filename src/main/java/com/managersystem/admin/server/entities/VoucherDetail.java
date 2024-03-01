package com.managersystem.admin.server.entities;

import com.managersystem.admin.server.entities.base.BaseEntity;
import com.managersystem.admin.server.entities.type.RewardItemStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "voucher_detail")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class VoucherDetail extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "store_id")
  private Long storeId;

  @Column(name = "name")
  private String name;

  @Column(name = "code")
  private String code;

  @Column(name = "user_id")
  private UUID userId;

  @Column(name = "given_at")
  private Long givenAt;

  @Column(name = "voucher_status")
  @Enumerated(EnumType.STRING)
  private RewardItemStatus status;

  @Column(name = "given_to_pool")
  private Long givenToPool;

  @Column(name = "reward_segment_id")
  private Long rewardSegmentId;

  @Column(name = "reward_item_id")
  private Long rewardItemId;

  @Column(name = "start_at")
  private Long startAt;

  @Column(name = "expire_at")
  private Long expireAt;
}
