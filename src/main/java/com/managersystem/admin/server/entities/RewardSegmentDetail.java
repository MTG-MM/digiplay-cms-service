package com.managersystem.admin.server.entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "reward_segment_detail")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RewardSegmentDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "priority")
  private Long priority; //do uu tien nhan qua theo ti le

  @Column(name = "segment_rate")
  private Long segmentRate; //do uu tien nhan qua theo loai nguoi dung

  @Column(name = "position")
  private Long position;

  @Column(name = "reward_item_id")
  private Long rewardItemId;

  @Column(name = "reward_segment_id")
  private Long rewardSegmentId;
}
