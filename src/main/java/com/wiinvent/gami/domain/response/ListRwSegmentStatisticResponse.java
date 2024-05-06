package com.wiinvent.gami.domain.response;

import lombok.Data;

import java.util.List;

@Data
public class ListRwSegmentStatisticResponse {
  private Long rwSegmentId;
  private String rwSegmentName;
  private List<ListRwItemStatisticResponse> rwItemList;
}
