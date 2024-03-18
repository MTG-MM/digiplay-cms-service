package com.wiinvent.gami.domain.entities;

import com.wiinvent.gami.domain.entities.base.BaseEntity;
import com.wiinvent.gami.domain.entities.type.FriendStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "friend")
public class Friend extends BaseEntity {
  @Id
  @Column(name = "id", nullable = false, columnDefinition = "binary(16)")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotNull
  @Column(name = "user_id", nullable = false, columnDefinition = "binary(16)")
  private UUID userId;

  @NotNull
  @Column(name = "friend_id", nullable = false, columnDefinition = "binary(16)")
  private UUID friendId;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private FriendStatus status;

}