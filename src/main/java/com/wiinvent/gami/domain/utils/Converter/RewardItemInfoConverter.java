package com.wiinvent.gami.domain.utils.Converter;

import com.wiinvent.gami.domain.pojo.RewardItemInfo;
import com.wiinvent.gami.domain.utils.JsonParser;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RewardItemInfoConverter implements AttributeConverter<RewardItemInfo, String> {
  @Override
  public String convertToDatabaseColumn(RewardItemInfo rewardItemInfo) {
    return JsonParser.toJson(rewardItemInfo);
  }

  @Override
  public RewardItemInfo convertToEntityAttribute(String dbData) {
    return JsonParser.entity(dbData, RewardItemInfo.class);
  }
}
