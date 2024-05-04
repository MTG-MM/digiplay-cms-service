package com.wiinvent.gami.domain.entities.transaction;

import com.wiinvent.gami.domain.entities.BaseEntity;
import com.wiinvent.gami.domain.entities.type.ResourceType;
import com.wiinvent.gami.domain.entities.type.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "collection_transaction")
public class CollectionTransaction extends BaseEntity {
  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @Size(max = 16)
  @NotNull
  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "amount")
  private Integer amount;

  @Column(name = "balance")
  private Integer balance;

  @Lob
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
