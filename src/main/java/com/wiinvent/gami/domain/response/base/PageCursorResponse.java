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

  public PageCursorResponse(List<T> data, Integer pageSize, Long next, Long pre, String fieldName) {
    this.data = data;
    CursorType type = determineCursorType(next, pre);
    setPageSize(pageSize);
    adjustMetadata(type, fieldName);
  }

  private CursorType determineCursorType(Long next, Long pre) {
    if (next == null && pre == null) {
      return CursorType.FIRST;
    } else if (next != null) {
      return CursorType.NEXT;
    } else {
      return CursorType.PRE;
    }
  }

  private void setPageSize(Integer pageSize) {
    if (pageSize != null) {
      metadata.setPageSize(pageSize);
    }
  }

  private void adjustMetadata(CursorType type, String fieldName) {
    if (!data.isEmpty()) {
      try {
        T firstObject = data.getFirst();
        T lastObject = data.getLast();
        Field field = firstObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        if (metadata.getHasNextPage() && type.equals(CursorType.NEXT)) {
          metadata.setNext((Long) field.get(lastObject));
        }
        if (metadata.getHasPrePage() && type.equals(CursorType.PRE)) {
          metadata.setPre((Long) field.get(firstObject));
        }
      } catch (Exception ex) {
        handleException(ex);
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

  private void handleException(Exception ex) {
    // Handle exception as needed, such as logging or throwing a custom exception
  }
}
