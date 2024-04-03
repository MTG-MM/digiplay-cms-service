package com.wiinvent.gami.domain.utils.Converter;

import com.wiinvent.gami.domain.entities.payment.PaymentMethodInfo;
import com.wiinvent.gami.domain.utils.JsonParser;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PaymentMethodInfoConverter implements AttributeConverter<PaymentMethodInfo, String> {
  @Override
  public String convertToDatabaseColumn(PaymentMethodInfo paymentMethodInfo) {
    return JsonParser.toJson(paymentMethodInfo);
  }

  @Override
  public PaymentMethodInfo convertToEntityAttribute(String dbData) {
    return JsonParser.entity(dbData, PaymentMethodInfo.class);
  }
}
