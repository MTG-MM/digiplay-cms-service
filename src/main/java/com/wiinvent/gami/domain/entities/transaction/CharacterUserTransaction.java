package com.wiinvent.gami.domain.entities.transaction;

import com.wiinvent.gami.domain.entities.BaseEntity;
import com.wiinvent.gami.domain.entities.type.ResourceType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "character_user_transaction")
public class CharacterUserTransaction extends BaseEntity {
  @Id
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @NotNull
  @Column(name = "character_id", nullable = false)
  private Integer characterId;

  @Column(name = "amount")
  private Integer amount;

  @Column(name = "balance")
  private Integer balance;

  @Size(max = 50)
  @Column(name = "resource_type", length = 50)
  @Enumerated(EnumType.STRING)
  private ResourceType resourceType;

  @Size(max = 500)
  @Column(name = "note", length = 500)
  private String note;
}