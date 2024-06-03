package com.wiinvent.gami.domain.entities.transaction;

import com.wiinvent.gami.domain.entities.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@Entity
@Table(name = "character_user")
public class CharacterUser extends BaseEntity {
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "character_id", nullable = false)
  private Integer characterId;

  @Column(name = "quantity")
  private Integer quantity;

  @Column(name = "expire_time")
  private Long expireTime;
}
