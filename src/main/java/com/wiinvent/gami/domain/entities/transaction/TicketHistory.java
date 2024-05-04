package com.wiinvent.gami.domain.entities.transaction;

import com.wiinvent.gami.domain.entities.BaseEntity;
import com.wiinvent.gami.domain.entities.type.ResourceType;
import com.wiinvent.gami.domain.entities.type.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;
@Data
@Entity
@Table(name = "ticket_history")
public class TicketHistory extends BaseEntity {
  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "user_id")
  private UUID userId;

  @Column(name = "amount")
  private Integer amount;

  @Column(name = "balance")
  private Integer balance;

  @Enumerated(EnumType.STRING)
  @Column(name = "resource_type")
  private ResourceType resourceType;

  @Size(max = 255)
  @Column(name = "note")
  private String note;

  @Lob
  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private TransactionType type;
}
