package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.PackageStateType;
import com.wiinvent.gami.domain.entities.type.PaymentMethodType;
import com.wiinvent.gami.domain.pojo.PackageInfo;
import lombok.Data;

import java.util.UUID;

@Data
public class PackageHistoryResponse {
  private UUID id;
  private String packageCode;
  private PackageInfo packageInfo;
  private Integer price;
  private PaymentMethodType paymentMethod;
  private PackageStateType stateType;
  private Long expireAt;
  private Long createdAt;
}
