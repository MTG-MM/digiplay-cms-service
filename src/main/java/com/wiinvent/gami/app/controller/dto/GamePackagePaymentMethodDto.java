package com.wiinvent.gami.app.controller.dto;

import com.wiinvent.gami.domain.entities.type.PaymentMethodType;
import lombok.Data;

@Data
public class GamePackagePaymentMethodDto {

  PaymentMethodType type;

  Long price;

  Long currency;
}
