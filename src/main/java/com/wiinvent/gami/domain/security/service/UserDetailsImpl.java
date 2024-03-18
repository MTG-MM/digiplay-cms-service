package com.wiinvent.gami.domain.security.service;

import com.wiinvent.gami.domain.entities.Account;
import com.wiinvent.gami.domain.entities.type.AccountRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class UserDetailsImpl implements UserDetails {
  @Setter
  @Getter
  private UUID id;
  private String username;
  private String password;
  @Setter
  @Getter
  private AccountRole accountRole;
  private List<SimpleGrantedAuthority> authorities;

  public UserDetailsImpl(Account account) {
    id = account.getId();
    username = account.getUsername();
    password = account.getPassword();
    accountRole = account.getRole();
    authorities = Stream.of(account.getRole())
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
        .toList();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}

