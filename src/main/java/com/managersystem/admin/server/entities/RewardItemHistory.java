package com.managersystem.admin.server.entities;


import com.managersystem.admin.server.entities.base.BaseEntity;
import com.managersystem.admin.server.entities.type.RewardType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "reward_item_histoty")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RewardItemHistory extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "reward_name")
  private String name;

  @Column(name = "reward_type")
  @Enumerated(EnumType.STRING)
  private RewardType type;

  @Column(name = "user_id")
  private UUID userId;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "note")
  private String note;

  @Column(name = "reward_info")
  private String rewardInfo;
}
