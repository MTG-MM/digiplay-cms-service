package com.managersystem.admin.server.entities;

import com.managersystem.admin.server.entities.base.BaseEntity;
import com.managersystem.admin.server.entities.type.PeriodType;
import com.managersystem.admin.server.utils.DateUtils;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "reward_schedule")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RewardSchedule {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "reward_segment_detail_id")
  private Long rewardSegmentDetailId;

  @Column(name = "period_type")
  @Enumerated(EnumType.STRING)
  private PeriodType periodType;

  @Column(name = "quantity")
  private Long quantity;

  @Column(name = "is_accumulative")
  private Boolean isAccumulative; // true thì sẽ tích lũy số lượng của ngày hôm trước vào ngày hôm sau

  @Column(name = "start_at")
  private Long startAt;

  @Column(name = "end_at")
  private Long endAt;

  @Column(name = "created_at")
  private Long createdAt = DateUtils.getNowMillisAtUtc();

  @Column(name = "updated_at")
  private Long updatedAt = DateUtils.getNowMillisAtUtc();
}
