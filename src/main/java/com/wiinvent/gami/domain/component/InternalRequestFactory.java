package com.wiinvent.gami.domain.component;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "internalRequestFactory", url = "http://localhost:8080")
public interface InternalRequestFactory {

  @RequestLine("POST api/mos/it/reward-schedule/process")
  void updateSegmentSchedule();
}
