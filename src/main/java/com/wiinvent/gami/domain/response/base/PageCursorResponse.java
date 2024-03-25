package com.wiinvent.gami.domain.response.base;

import com.wiinvent.gami.domain.response.type.CursorType;
import com.wiinvent.gami.domain.utils.DateUtils;
import lombok.Data;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.List;

@Getter
public class PageCursorResponse<T> {
  List<T> data;

  Metadata metadata = new Metadata();

  @Data
  public static class Metadata {
    Long next;
    Long pre;
    Boolean hasNextPage = true;
    Boolean hasPrePage = true;
    Integer pageSize = 20;
  }

  public PageCursorResponse(List<T> data, Integer pageSize, CursorType type, String fieldName) {
    this.data = data;
    if (pageSize != null) {
      metadata.setPageSize(pageSize);
    }
    if (type.equals(CursorType.FIRST)) {
      type = CursorType.NEXT;
      metadata.setHasPrePage(false);
    }
    if (type.equals(CursorType.NEXT)) {
      if (data.size() < metadata.getPageSize()) {
        metadata.setHasNextPage(false);
      }
    } else {
      if (data.size() < metadata.getPageSize()) {
        metadata.setHasPrePage(false);
      }
    }

    if (!data.isEmpty()) {
      try {
        T firstObject = data.getFirst();
        T lastObject = data.getLast();
        Field field = firstObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        if (metadata.getHasNextPage()) {
          metadata.setNext((Long) field.get(lastObject));
        }
        if (metadata.getHasPrePage()) {
          metadata.setPre((Long) field.get(firstObject));
        }
      } catch (Exception ex) {

      }
    } else {
      if (metadata.getHasNextPage()) {
        metadata.setNext(DateUtils.getNowMillisAtUtc());
      }
      if (metadata.getHasPrePage()) {
        metadata.setPre(0L);
      }
    }
  }

}
