package com.managersystem.admin.server.entities;

import com.managersystem.admin.server.entities.base.BaseEntity;
import com.managersystem.admin.server.entities.type.PeriodLimitType;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "reward_segment_detail")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RewardSegmentDetail extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "priority")
  private Long priority = 0L; //do uu tien nhan qua theo ti le

  @Column(name = "segment_rate")
  private Long segmentRate = 0L; //do uu tien nhan qua theo loai nguoi dung

  @Column(name = "position")
  private Long position;

  @Column(name = "reward_item_id")
  private Long rewardItemId;

  @Column(name = "is_default", columnDefinition = "BIT")
  private Boolean isDefault = false; //Là quà mặc định sẽ nhả ra nếu không còn quà

  @Column(name = "period_type")
  @Enumerated(EnumType.STRING)
  private PeriodLimitType periodType = PeriodLimitType.DAY; //Khoảng thời gian

  @Column(name = "period_number")
  private Long periodNumber = 1L; //Số khoảng thời gian (periodNumber = 3, periodType = DAY => 3 ngay nhan duoc toi da {periodValue} qua)

  @Column(name = "period_value")
  private Long periodValue = 1L; //Số quà tối đa người dùng có thể nhận trong khoảng thời gian

  @Column(name = "reward_segment_id")
  private Long rewardSegmentId;

  @Transient
  private Long updatePriority = 0L;
}
