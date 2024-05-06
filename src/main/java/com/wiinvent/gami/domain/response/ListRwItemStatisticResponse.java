package com.wiinvent.gami.domain.response;

import lombok.Data;

import java.util.List;

@Data
public class ListRwItemStatisticResponse {
  private Long rewardItemId;
  private List<StatisticResponse> statisticResponseList;
}
