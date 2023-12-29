package com.managersystem.admin.server.security;

import com.managersystem.admin.server.entities.AccountEntity;
import com.managersystem.admin.server.stores.AccountStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserSecurityService implements UserDetailsService {

  @Autowired
  private AccountStorage accountStorage;

  @Autowired
  private PasswordEncoder encoder;

  @Override
  public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

    AccountEntity account = accountStorage.findById(UUID.fromString(id));

    if(account == null){
      throw new UsernameNotFoundException("User not found " + id);
    }
    return new UserDetailsImpl(account);
  }

  public String encode(String password) {
    return encoder.encode(password);
  }

  public boolean decode(String rawPassword, String encodedPassword) {
    return encoder.matches(rawPassword, encodedPassword);
  }
}

