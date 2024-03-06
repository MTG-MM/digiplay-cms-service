package com.managersystem.admin.server.entities;

import com.managersystem.admin.server.entities.type.Status;
import com.managersystem.admin.server.utils.DateUtils;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "reward_segment")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RewardSegment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "code")
  private String code;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "is_accumulative_priority", columnDefinition = "BIT")
  private Boolean isAccumulativePriority; //Có tích lũy vào quà default nếu quà không hợp lệ không

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private Status status;

  @Column(name = "created_at")
  private Long createdAt = DateUtils.getNowMillisAtUtc();

  @Column(name = "updated_at")
  private Long updatedAt = DateUtils.getNowMillisAtUtc();
}
