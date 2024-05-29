package com.wiinvent.gami.domain.dto.payment;

import com.wiinvent.gami.domain.entities.type.PaymentMethodType;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentMethodCreateDto {
  @NotEmpty(message = "không được để trống")
  private String name;
  @Min(0)
  private Integer priority;

  private String imageUrl;

  @NotNull(message = "không được để trống")
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

  @NotNull(message = "không được để trống")
  private Status status;
}