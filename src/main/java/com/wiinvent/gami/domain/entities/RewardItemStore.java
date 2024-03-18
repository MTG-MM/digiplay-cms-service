package com.wiinvent.gami.domain.entities;

import com.wiinvent.gami.domain.entities.base.BaseEntity;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.entities.type.StoreType;
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
