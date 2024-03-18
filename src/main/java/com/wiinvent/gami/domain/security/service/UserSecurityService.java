package com.wiinvent.gami.domain.security.service;

import com.wiinvent.gami.domain.entities.Account;
import com.wiinvent.gami.domain.stores.AccountStorage;
import com.wiinvent.gami.domain.utils.CacheKey;
import com.wiinvent.gami.domain.utils.DateUtils;
import com.wiinvent.gami.domain.utils.RemoteCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class UserSecurityService implements UserDetailsService {

  @Autowired private AccountStorage accountStorage;
  @Autowired private PasswordEncoder encoder;
  @Autowired private RemoteCache remoteCache;
  @Autowired private CacheKey cacheKey;

  @Override
  public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

    Account userAccount = accountStorage.findById(UUID.fromString(id));

    if(userAccount == null){
      throw new UsernameNotFoundException("User not found " + id);
    }
    return new UserDetailsImpl(userAccount);
  }

  public String encode(String password) {
    return encoder.encode(password);
  }

  public boolean decode(String rawPassword, String encodedPassword) {
    return encoder.matches(rawPassword, encodedPassword);
  }
}

