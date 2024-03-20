package com.wiinvent.gami.domain.entities;

import com.wiinvent.gami.domain.entities.type.UserNotifyType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user_notify")
public class UserNotify extends BaseEntity {
  @Id
  @Column(name = "id", nullable = false)
  private String id;

  @NotNull
  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Size(max = 100)
  @Column(name = "title", length = 100)
  private String title;

  @Size(max = 250)
  @Column(name = "content", length = 250)
  private String content;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", length = 30)
  private UserNotifyType type;

}