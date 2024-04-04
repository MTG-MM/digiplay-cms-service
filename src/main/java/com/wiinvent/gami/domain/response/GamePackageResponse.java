package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.payment.PaymentMethodInfo;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class GamePackageResponse{
  private Integer id;

  private Integer gameId;

  private String code;

  private Integer price;

  private Integer point;

  private Integer coin;

  private String imageUrl;

  private String thumbUrl;

  private String description;

  private Status status;

  private Integer priority;

  private List<PaymentMethodInfo> paymentMethodInfo;
}
