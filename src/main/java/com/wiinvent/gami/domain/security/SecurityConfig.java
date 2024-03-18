package com.wiinvent.gami.domain.security;

import com.wiinvent.gami.domain.exception.GlobalExceptionHandler;
import com.wiinvent.gami.domain.security.jwt.CustomAuthenticationEntryPoint;
import com.wiinvent.gami.domain.security.jwt.JwtAuthFilter;
import com.wiinvent.gami.domain.security.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  private final GlobalExceptionHandler globalExceptionHandler;

  @Autowired
  public SecurityConfig(GlobalExceptionHandler globalExceptionHandler) {
    this.globalExceptionHandler = globalExceptionHandler;
  }

  @Bean
  public CustomAuthenticationEntryPoint authEntryPoint() {
    return new CustomAuthenticationEntryPoint(globalExceptionHandler);
  }

  @Bean
  public JwtAuthFilter authFilter() {
    return new JwtAuthFilter();
  }

  // User Creation
  @Bean
  public UserDetailsService userDetailsService() {
    return new UserSecurityService();
  }


  @Bean
  public SecurityFilterChain jwtSecurityFilterChain(HttpSecurity http) throws Exception {
    return http.cors(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests()
        .requestMatchers(
            "/api/vt/it/**",
            "/api/vt/ext/**",
            "/login/**",
            "/v1/docs/**").permitAll()
        .and()
        .authorizeHttpRequests().anyRequest().authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .exceptionHandling().authenticationEntryPoint(authEntryPoint())
        .and()
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class).build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }


}
