package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.payment.PaymentMethodInfo;
import com.wiinvent.gami.domain.entities.type.PackageType;
import com.wiinvent.gami.domain.entities.type.Status;
import lombok.Data;

import java.util.List;

@Data
public class PackageResponse {
  private Integer id;
  private String code;
  private String imageUrl;
  private String thumbUrl;
  private Integer daySub;
  private Status status;
  private PackageType packageType;
  private List<PaymentMethodInfo> paymentMethodInfo;;

}
