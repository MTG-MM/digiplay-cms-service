package com.managersystem.admin.server.entities;

import com.managersystem.admin.server.entities.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table( name = "user_segment")
@Entity
public class UserSegment extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "is_default")
  private Boolean isDefault;

  @Column(name = "min_priority")
  private Long minPriority;

  @Column(name = "max_priority")
  private Long maxPriority;
}
