package com.wiinvent.gami.domain.utils.Converter;

import com.wiinvent.gami.domain.pojo.GamePackageInfo;
import com.wiinvent.gami.domain.utils.JsonParser;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class GamePackageInfoConverter implements AttributeConverter<GamePackageInfo, String> {
  @Override
  public String convertToDatabaseColumn(GamePackageInfo gamePackageInfo) {
    return JsonParser.toJson(gamePackageInfo);
  }

  @Override
  public GamePackageInfo convertToEntityAttribute(String dbData) {
    return JsonParser.entity(dbData, GamePackageInfo.class);
  }
}
