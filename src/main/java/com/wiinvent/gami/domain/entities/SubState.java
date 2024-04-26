package com.wiinvent.gami.domain.entities;

import com.wiinvent.gami.domain.entities.type.PackageStateType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "sub_state")
public class SubState extends BaseEntity{
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotNull
  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "package_id")
  private Integer packageId;

  @Column(name = "start_at")
  private Long startAt;

  @Column(name = "end_at")
  private Long endAt;

  @Column(name = "sub_state")
  @Enumerated(EnumType.STRING)
  private PackageStateType subState;
}
