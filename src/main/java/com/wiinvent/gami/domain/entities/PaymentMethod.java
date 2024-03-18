package com.wiinvent.gami.domain.entities;

import com.wiinvent.gami.domain.entities.base.BaseEntity;
import com.wiinvent.gami.domain.entities.type.PaymentMethodType;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payment_method")
public class PaymentMethod extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Size(max = 200)
  @Column(name = "name", length = 200)
  private String name;

  @Column(name = "priority")
  private Integer priority;

  @Size(max = 500)
  @Column(name = "image_url", length = 500)
  private String imageUrl;

  @Size(max = 50)
  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "payment_method_type", nullable = false, length = 50)
  private PaymentMethodType paymentMethodType;

  @Size(max = 50)
  @Column(name = "username", length = 50)
  private String username;

  @Size(max = 50)
  @Column(name = "password", length = 50)
  private String password;

  @Size(max = 200)
  @Column(name = "public_key", length = 200)
  private String publicKey;

  @Size(max = 500)
  @Column(name = "link_api_verify", length = 500)
  private String linkApiVerify;

  @Size(max = 2000)
  @Column(name = "body_api_verify", length = 2000)
  private String bodyApiVerify;

  @Size(max = 500)
  @Column(name = "link_api_request_payment", length = 500)
  private String linkApiRequestPayment;

  @Size(max = 2000)
  @Column(name = "body_api_request_payment", length = 2000)
  private String bodyApiRequestPayment;

  @Size(max = 500)
  @Column(name = "link_api_request_confirm", length = 500)
  private String linkApiRequestConfirm;

  @Size(max = 2000)
  @Column(name = "body_api_request_confirm", length = 2000)
  private String bodyApiRequestConfirm;

  @Lob
  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private Status status;

}