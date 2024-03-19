package com.wiinvent.gami.domain.security.jwt;

import com.wiinvent.gami.domain.exception.GlobalExceptionHandler;
import com.wiinvent.gami.domain.exception.base.BaseException;
import com.wiinvent.gami.domain.utils.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Log4j2
@Configuration
public class AuthedEntryPoint implements AuthenticationEntryPoint, AccessDeniedHandler {

  private final GlobalExceptionHandler globalExceptionHandler;
  @Autowired
  public AuthedEntryPoint(GlobalExceptionHandler globalExceptionHandler) {
    this.globalExceptionHandler = globalExceptionHandler;
  }

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
    globalExceptionHandler.authenticationException(new BaseException(Constant.INVALID_AUTHENTICATION, 401), response);
  }

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    globalExceptionHandler.authenticationException(new BaseException(Constant.INVALID_PERMISSION, 403), response);
  }
}
