package com.managersystem.admin.server.stores.base;

import com.managersystem.admin.server.repositories.AccountRepository;
import com.managersystem.admin.server.repositories.ApplicationRepository;
import com.managersystem.admin.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseStorage {

  @Autowired protected AccountRepository accountRepository;
  @Autowired protected UserRepository userRepository;
  @Autowired protected ApplicationRepository applicationRepository;
}
