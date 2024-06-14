package com.wiinvent.gami.domain.entities.transaction;

import com.wiinvent.gami.domain.entities.BaseEntity;
import com.wiinvent.gami.domain.entities.type.ResourceType;
import com.wiinvent.gami.domain.entities.type.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "gold_pig_transaction")
public class GoldPigTransaction extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false, length = 16)
  private UUID id;

  @NotNull
  @Column(name = "user_id", nullable = false, length = 16)
  private UUID userId;

  @Column(name = "amount")
  private Integer amount;

  @Column(name = "balance")
  private Integer balance;

  @Enumerated(EnumType.STRING)
  @Column(name = "resource_type")
  private ResourceType resourceType;

  @Lob
  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private TransactionType type;

  @Size(max = 500)
  @Column(name = "note", length = 500)
  private String note;
}
