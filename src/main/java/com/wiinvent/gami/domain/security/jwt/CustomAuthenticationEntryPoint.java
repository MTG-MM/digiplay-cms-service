package com.wiinvent.gami.domain.security.jwt;

import com.wiinvent.gami.domain.exception.GlobalExceptionHandler;
import com.wiinvent.gami.domain.exception.base.BaseException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Log4j2
@Configuration
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final GlobalExceptionHandler globalExceptionHandler;
  @Autowired
  public CustomAuthenticationEntryPoint(GlobalExceptionHandler globalExceptionHandler) {
    this.globalExceptionHandler = globalExceptionHandler;
  }

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
    globalExceptionHandler.authenticationException(new BaseException("Unauthorized", 401), response);
  }


}
