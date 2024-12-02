package com.wiinvent.gami.domain.factory;

import com.wiinvent.gami.domain.pojo.GoogleCaptchaResponse;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "recaptchaRequestFactory", url = "${spring.security.recaptcha.url}")
public interface RecaptchaRequestFactory {
  @RequestLine("POST /recaptcha/api/siteverify?secret={secret}&response={response}")
  GoogleCaptchaResponse processCheckRecaptcha(@Param("secret") String secret, @Param("response") String response);
}
