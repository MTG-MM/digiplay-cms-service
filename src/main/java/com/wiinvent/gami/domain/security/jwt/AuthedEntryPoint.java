package com.wiinvent.gami.domain.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wiinvent.gami.domain.exception.GlobalExceptionHandler;
import com.wiinvent.gami.domain.exception.base.BaseException;
import com.wiinvent.gami.domain.exception.base.ExceptionResponse;
import com.wiinvent.gami.domain.utils.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
@Log4j2
@Configuration
public class AuthedEntryPoint implements AuthenticationEntryPoint, AccessDeniedHandler {


  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    OutputStream responseStream = response.getOutputStream();
    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(responseStream, ExceptionResponse.createFrom(new BaseException(Constants.INVALID_TOKEN, 401 )));
    responseStream.flush();  }

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    OutputStream responseStream = response.getOutputStream();
    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(responseStream, ExceptionResponse.createFrom(new BaseException(Constants.INVALID_PERMISSION, 403 )));
    responseStream.flush();  }
}
