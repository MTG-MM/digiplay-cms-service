package com.wiinvent.gami.domain.entities;

import com.wiinvent.gami.domain.entities.base.BaseEntity;
import com.wiinvent.gami.domain.entities.type.PaymentMethodType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payment_transaction")
public class PaymentTransaction extends BaseEntity {
  @Id
  @Size(max = 16)
  @Column(name = "id", nullable = false, length = 16)
  private String id;

  @Size(max = 20)
  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "payment_method", nullable = false, length = 20)
  private PaymentMethodType paymentMethod;

  @Size(max = 20)
  @NotNull
  @Column(name = "payment_partner_transaction_id", nullable = false, length = 20)
  private String paymentPartnerTransactionId;

  @Size(max = 20)
  @NotNull
  @Column(name = "package_code", nullable = false, length = 20)
  private String packageCode;

  @Size(max = 500)
  @Column(name = "package_info", length = 500)
  private String packageInfo;

  @Size(max = 16)
  @NotNull
  @Column(name = "user_id", nullable = false, length = 16)
  private String userId;

  @Column(name = "price")
  private Integer price;

}