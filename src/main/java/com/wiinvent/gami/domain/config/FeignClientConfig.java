package com.wiinvent.gami.domain.config;

import feign.Contract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {
  @Bean
  public Contract feignContract() {
    return new Contract.Default();
  }
}
