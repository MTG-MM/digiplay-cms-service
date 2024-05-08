package com.wiinvent.gami.domain.entities.reward;


import com.wiinvent.gami.domain.entities.BaseEntity;
import com.wiinvent.gami.domain.entities.type.RewardItemType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.utils.DateUtils;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "reward_type")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RewardType extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private RewardItemType type;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private Status status;
}
