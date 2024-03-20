package com.wiinvent.gami.domain.entities.game;

import com.wiinvent.gami.domain.entities.BaseEntity;
import com.wiinvent.gami.domain.entities.type.PaymentStatusType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "game_payment_transaction")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GamePaymentTransaction extends BaseEntity {
  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @Size(max = 50)
  @NotNull
  @Column(name = "partner_method", nullable = false, length = 50)
  private String partnerMethod;

  @NotNull
  @Column(name = "game_id", nullable = false)
  private Integer gameId;

  @NotNull
  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "payment_transaction_id")
  private String paymentTransactionId;

  @Column(name = "game_transaction_id")
  private String gameTransactionId;

  @NotNull
  @Column(name = "package_id", nullable = false, length = 20)
  private Integer packageId;

  @Lob
  @Enumerated(EnumType.STRING)
  @Column(name = "payment_status")
  private PaymentStatusType paymentStatus;

  @Size(max = 500)
  @Column(name = "package_info", length = 500)
  private String packageInfo;

  @Column(name = "price")
  private Integer price;

}