package com.wiinvent.gami.domain.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;


@Configuration
@Slf4j
public class RestTemplateConfig {
//  @Bean
//  public RestTemplate rest(RestTemplateBuilder builder) {
//    return builder.build();
//  }

  @Value("${ok.http.connect-timeout}")
  private Integer connectTimeout;

  @Value("${ok.http.read-timeout}")
  private Integer readTimeout;

  @Value("${ok.http.write-timeout}")
  private Integer writeTimeout;

  @Value("${ok.http.max-idle-connections}")
  private Integer maxIdleConnections;

  @Value("${ok.http.keep-alive-duration}")
  private Long keepAliveDuration;

  @Bean
  public RestTemplate httpRestTemplate() {
    ClientHttpRequestFactory factory = httpRequestFactory();
    return new RestTemplate(factory);
  }

  public ClientHttpRequestFactory httpRequestFactory() {
    return new OkHttp3ClientHttpRequestFactory(okHttpConfigClient());
  }

  public OkHttpClient okHttpConfigClient(){
    return new OkHttpClient().newBuilder()
        .connectionPool(pool())
        .connectTimeout(connectTimeout, TimeUnit.SECONDS)
        .readTimeout(readTimeout, TimeUnit.SECONDS)
        .writeTimeout(writeTimeout, TimeUnit.SECONDS)
        .hostnameVerifier((hostname, session) -> true)
        .build();
  }

  public ConnectionPool pool() {
    return new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.SECONDS);
  }
}
