package com.wiinvent.gami.domain.entities.payment;

import com.wiinvent.gami.domain.entities.BaseEntity;
import com.wiinvent.gami.domain.entities.type.PaymentMethodType;
import com.wiinvent.gami.domain.pojo.PackageInfo;
import com.wiinvent.gami.domain.utils.Converter.PackageInfoConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "package_history")
public class PackageHistory extends BaseEntity {
  @Id
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "payment_method", nullable = false, length = 20)
  private PaymentMethodType paymentMethod;

  @NotNull
  @Column(name = "payment_partner_transaction_id", nullable = false, length = 20)
  private String paymentPartnerTransactionId;

  @NotNull
  @Column(name = "package_code", nullable = false, length = 20)
  private String packageCode;

  @Size(max = 500)
  @Column(name = "package_info", length = 500)
  @Convert(converter = PackageInfoConverter.class)
  private PackageInfo packageInfo;

  @NotNull
  @Column(name = "user_id", columnDefinition = "BINARY(16)")
  private UUID userId;

  @Column(name = "price")
  private Integer price;

}