package com.wiinvent.gami.domain.utils.Converter;

import com.wiinvent.gami.domain.pojo.AchievementInfo;
import com.wiinvent.gami.domain.utils.JsonParser;
import jakarta.persistence.AttributeConverter;

import java.util.List;

public class AchievementInfoConverter implements AttributeConverter<List<AchievementInfo>, String> {
  @Override
  public String convertToDatabaseColumn(List<AchievementInfo> attribute) {
    return JsonParser.toJson(attribute);
  }

  @Override
  public List<AchievementInfo> convertToEntityAttribute(String dbData) {
    return JsonParser.arrayList(dbData, AchievementInfo.class);
  }
}
