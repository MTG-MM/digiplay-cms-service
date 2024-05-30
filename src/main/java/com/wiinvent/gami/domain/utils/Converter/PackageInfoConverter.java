package com.wiinvent.gami.domain.utils.Converter;

import com.wiinvent.gami.domain.pojo.PackageInfo;
import com.wiinvent.gami.domain.utils.JsonParser;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PackageInfoConverter implements AttributeConverter<PackageInfo, String> {
  @Override
  public String convertToDatabaseColumn(PackageInfo packageInfo) {
    return JsonParser.toJson(packageInfo);
  }

  @Override
  public PackageInfo convertToEntityAttribute(String dbData) {
    return JsonParser.entity(dbData, PackageInfo.class);
  }
}
