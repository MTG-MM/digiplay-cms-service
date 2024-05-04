package com.wiinvent.gami.domain.entities.user;

import com.wiinvent.gami.domain.entities.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "collection_user")
@Data
public class UserCollection extends BaseEntity {
  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "collection_id")
  private Long collectionId;

  @Size(max = 16)
  @Column(name = "user_id", length = 16)
  private UUID userId;

  @Column(name = "quantity")
  private Integer quantity;

  public void addQuantity(int amount) {
    this.quantity += amount;
  }
  public void minusQuantity(int amount) {
    this.quantity = Math.min(this.quantity - amount, 0);
  }
}
