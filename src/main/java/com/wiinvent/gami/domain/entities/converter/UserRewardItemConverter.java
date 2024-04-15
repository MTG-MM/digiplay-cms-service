package com.wiinvent.gami.domain.entities.converter;

import com.wiinvent.gami.domain.pojo.UserRewardItems;
import com.wiinvent.gami.domain.utils.JsonParser;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter
public class UserRewardItemConverter implements AttributeConverter<List<UserRewardItems>, String> {

  @Override
  public String convertToDatabaseColumn(List<UserRewardItems> rewardDetailInfos) {
    return JsonParser.toJson(rewardDetailInfos);
  }

  @Override
  public List<UserRewardItems> convertToEntityAttribute(String dbData) {
    return JsonParser.arrayList(dbData, UserRewardItems.class);
  }
}