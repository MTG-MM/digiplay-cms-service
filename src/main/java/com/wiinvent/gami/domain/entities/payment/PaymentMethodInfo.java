package com.wiinvent.gami.domain.entities.payment;

import com.wiinvent.gami.domain.entities.type.PaymentMethodType;
import lombok.Data;

@Data
public class PaymentMethodInfo {
  private String imageUrl;
  private PaymentMethodType type;
  private Long price;
  private String currency;
}
