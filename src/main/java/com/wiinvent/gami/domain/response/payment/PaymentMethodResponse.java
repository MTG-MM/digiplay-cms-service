package com.wiinvent.gami.domain.response.payment;

import com.wiinvent.gami.domain.entities.type.PaymentMethodType;
import com.wiinvent.gami.domain.entities.type.Status;
import lombok.Data;

@Data
public class PaymentMethodResponse {
  private Integer id;

  private String name;

  private Integer priority;

  private String imageUrl;

  private PaymentMethodType paymentMethodType;

  private String username;

  private String password;

  private String publicKey;

  private String linkApiVerify;

  private String bodyApiVerify;

  private String linkApiRequestPayment;

  private String bodyApiRequestPayment;

  private String linkApiRequestConfirm;

  private String bodyApiRequestConfirm;

  private Status status;

  private Long createdAt;

  private Long updatedAt;
}