package com.managersystem.admin.server.security;

import com.managersystem.admin.server.utils.DateUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// This class helps us to validate the generated jwt token
@Component
@Log4j2
public class JwtAuthFilter extends OncePerRequestFilter {

  @Value("${server.timeoutSlowApi:100}")
  private Integer timeoutSlowApi;

  @Autowired
  @Lazy
  private JwtService jwtService;

  @Autowired
  @Lazy
  private UserSecurityService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    long start = DateUtils.getNowMillisAtUtc();
    String authHeader = request.getHeader("Authorization");
    String token = null;
    String userId = null;
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      token = authHeader.substring(7);
      userId = jwtService.extractUserId(token);
    }

    if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
      if (jwtService.validateToken(token, userDetails)) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }
    filterChain.doFilter(request, response);
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

  public static String getPath(HttpServletRequest request) {
    StringBuilder requestURL = new StringBuilder(request.getRequestURI());
    String queryString = request.getQueryString();
    return queryString == null ? requestURL.toString() : requestURL.append('?').append(queryString).toString();
  }
}
