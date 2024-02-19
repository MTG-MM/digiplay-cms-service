package com.managersystem.admin.server.entities;

import com.managersystem.admin.server.entities.base.BaseEntity;
import com.managersystem.admin.server.entities.type.PollItemStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "product_detail")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProductDetail extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "store_id")
  private Long storeId;

  @Column(name = "name")
  private String name;

  @Column(name = "code")
  private String code;

  @Column(name = "user_id")
  private UUID userId;

  @Column(name = "given_at")
  private Long givenAt;

  @Column(name = "product_status")
  @Enumerated(EnumType.STRING)
  private PollItemStatus status;

  @Column(name = "start_at")
  private Long startAt;

  @Column(name = "expire_at")
  private Long expireAt;
}
