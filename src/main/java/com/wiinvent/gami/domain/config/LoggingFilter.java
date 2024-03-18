package com.wiinvent.gami.domain.config;

import com.wiinvent.gami.domain.exception.GlobalExceptionHandler;
import com.wiinvent.gami.domain.exception.base.BaseException;
import com.wiinvent.gami.domain.utils.DateUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
@Log4j2
public class LoggingFilter implements Filter {

  @Autowired
  GlobalExceptionHandler globalExceptionHandler;
  @Value("${server.timeoutSlowApi:100}")
  private Integer timeoutSlowApi;

  @Override
  public void init(FilterConfig filterConfig) {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    long start = DateUtils.getNowMillisAtUtc();

    try {
      filterChain.doFilter(request, response);
    } catch (BaseException e) {
      globalExceptionHandler.badRequestException(new BaseException(e.getMessage(), 400), response);
    } catch (Exception e) {
      log.error("Error processing: ", e);
      globalExceptionHandler.internalException(new BaseException("Đã có lỗi xảy ra", 500), response);
    } finally {
      long duration = DateUtils.getNowMillisAtUtc() - start;
      MDC.put("duration", duration + "");
      if (duration > timeoutSlowApi) {
        log.warn("end request ==> {}  {} {}", ((HttpServletRequest) request).getMethod(), getPath((HttpServletRequest) request), duration);
      } else {
        log.debug("end request ==> {}  {} {}", ((HttpServletRequest) request).getMethod(), getPath((HttpServletRequest) request), duration);
      }
      MDC.remove("duration");
      MDC.remove("userId");
    }


  }

  public static String getPath(HttpServletRequest request) {
    StringBuilder requestURL = new StringBuilder(request.getRequestURI());
    String queryString = request.getQueryString();
    return queryString == null ? requestURL.toString() : requestURL.append('?').append(queryString).toString();
  }

  @Override
  public void destroy() {
  }
}
