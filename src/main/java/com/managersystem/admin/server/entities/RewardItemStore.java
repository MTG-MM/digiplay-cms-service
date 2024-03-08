package com.managersystem.admin.server.entities;

import com.managersystem.admin.server.entities.base.BaseEntity;
import com.managersystem.admin.server.entities.type.Status;
import com.managersystem.admin.server.entities.type.StoreType;
import com.managersystem.admin.server.utils.DateUtils;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "reward_item_store")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RewardItemStore extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", columnDefinition = "BIGINT", nullable = false)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private StoreType type;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private Status status;
}
