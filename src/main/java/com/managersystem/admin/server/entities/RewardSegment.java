package com.managersystem.admin.server.entities;

import com.managersystem.admin.server.entities.base.BaseEntity;
import com.managersystem.admin.server.entities.type.Status;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "reward_segment")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RewardSegment extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "code")
  private String code;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private Status status;

}
