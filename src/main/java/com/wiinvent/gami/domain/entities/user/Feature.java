package com.wiinvent.gami.domain.entities.user;

import com.wiinvent.gami.domain.entities.BaseEntity;
import com.wiinvent.gami.domain.entities.type.FeatureCode;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "feature")
public class Feature extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "name", length = 30)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "code")
  private FeatureCode code;

  @NotNull
  @Column(name = "level_unlock", nullable = false)
  private Integer levelUnlock;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private Status status;
}