package com.wiinvent.gami.domain.entities.reward;


import com.wiinvent.gami.domain.entities.BaseEntity;
import com.wiinvent.gami.domain.entities.type.ResourceType;
import com.wiinvent.gami.domain.entities.type.RewardItemType;
import com.wiinvent.gami.domain.entities.type.RewardStateType;
import com.wiinvent.gami.domain.pojo.RewardItemInfo;
import com.wiinvent.gami.domain.utils.Converter.RewardItemInfoConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Table(name = "reward_item_history")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RewardItemHistory extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", columnDefinition = "BINARY(16)")
  private UUID id;

  @Column(name = "reward_name")
  private String name;

  @Column(name = "reward_type")
  @Enumerated(EnumType.STRING)
  private RewardItemType rewardItemType;

  @Column(name = "user_id", columnDefinition = "BINARY(16)")
  private UUID userId;

  @Column(name = "reward_item_id")
  private Long rewardItemId; // id reward item

  @Column(name = "reward_item_detail_id", columnDefinition = "BINARY(16)")
  private UUID rewardItemDetailId; // id qua trung thuong, (voucher <=> voucherDetailId, product <=> productDetailId)

  @Column(name = "reward_segment_id")
  private Long rewardSegmentId;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "note")
  private String note;

  @Column(name = "resource_type")
  @Enumerated(EnumType.STRING)
  private ResourceType resourceType;

  @Size(max = 2000)
  @Column(name = "reward_info", length = 2000)
  @Convert(converter = RewardItemInfoConverter.class)
  private RewardItemInfo rewardItemInfo;

  @Column(name = "reward_state")
  @Enumerated(EnumType.STRING)
  private RewardStateType rewardState;
}
