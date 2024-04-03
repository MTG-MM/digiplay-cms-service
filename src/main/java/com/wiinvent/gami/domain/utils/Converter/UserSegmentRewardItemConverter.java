package com.wiinvent.gami.domain.utils.Converter;

import com.wiinvent.gami.domain.entities.user.UserSegmentRewardItems;
import com.wiinvent.gami.domain.utils.JsonParser;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


import java.util.List;
@Converter
public class UserSegmentRewardItemConverter implements AttributeConverter<List<UserSegmentRewardItems>, String> {
  @Override
  public String convertToDatabaseColumn(List<UserSegmentRewardItems> rewardDetailInfos){
    return JsonParser.toJson(rewardDetailInfos);
  }

  @Override
  public List<UserSegmentRewardItems> convertToEntityAttribute(String dbData){
    return JsonParser.arrayList(dbData, UserSegmentRewardItems.class);
  }
}
