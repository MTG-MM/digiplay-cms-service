package com.wiinvent.gami.domain.utils.Converter;

import com.wiinvent.gami.domain.entities.payment.PaymentMethodInfo;
import com.wiinvent.gami.domain.utils.JsonParser;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter
public class PaymentMethodInfoConverter implements AttributeConverter<List<PaymentMethodInfo>, String> {
  @Override
  public String convertToDatabaseColumn(List<PaymentMethodInfo> paymentMethodInfo) {
    return JsonParser.toJson(paymentMethodInfo);
  }

  @Override
  public List<PaymentMethodInfo> convertToEntityAttribute(String dbData) {
    return JsonParser.arrayList(dbData, PaymentMethodInfo.class);
  }
}
