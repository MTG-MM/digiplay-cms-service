package com.managersystem.admin.server.utils;

import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class Helper {

  public static Map<String, Object> convertObjectToMap(Object object){
    try{
      Map<String, Object> map = new HashMap<>();
      Class<?> clazz = object.getClass();

      for (Field field : clazz.getDeclaredFields()) {
        field.setAccessible(true);
        String fieldName = field.getName();
        Object fieldValue = field.get(object);
        map.put(fieldName, fieldValue);
      }

      return map;
    }catch (Exception ex){
      log.error("=========>convertObjectToMap: ", ex);
      return new HashMap<>();
    }
  }

  // Chuyển Map về đối tượng
  public static <T> T convertMapToObject(Map<String, Object> map, Class<T> clazz) throws IllegalAccessException, InstantiationException {
    T object = clazz.newInstance();

    for (Field field : clazz.getDeclaredFields()) {
      field.setAccessible(true);
      String fieldName = field.getName();
      if (map.containsKey(fieldName)) {
        Object fieldValue = map.get(fieldName);
        field.set(object, fieldValue);
      }
    }

    return object;
  }


}
