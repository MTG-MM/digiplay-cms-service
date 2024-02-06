package com.managersystem.admin.server.entities;


import com.managersystem.admin.server.entities.base.BaseEntity;
import com.managersystem.admin.server.entities.type.Status;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "reward_state")
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
  private String type;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private Status status;
}
