package com.wiinvent.gami.domain.dto;

import com.wiinvent.gami.domain.entities.type.PaymentMethodType;
import lombok.Data;

@Data
public class GamePackagePaymentMethodDto {

  PaymentMethodType type;

  Long price;

  Long currency;
}
