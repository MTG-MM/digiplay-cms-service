package com.managersystem.admin.server.stores;

import com.managersystem.admin.server.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseStorage {

  @Autowired protected AccountRepository accountRepository;
}
