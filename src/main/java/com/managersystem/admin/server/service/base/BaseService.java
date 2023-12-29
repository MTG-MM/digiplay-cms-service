package com.managersystem.admin.server.service.base;

import com.managersystem.admin.ModelMapper;
import com.managersystem.admin.server.stores.AccountStorage;
import com.managersystem.admin.server.stores.IndustryGroupStorage;
import com.managersystem.admin.server.stores.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseService {
  @Autowired protected ModelMapper modelMapper;
  @Autowired protected AccountStorage accountStorage;
  @Autowired protected UserStorage userStorage;
  @Autowired protected IndustryGroupStorage industryGroupStorage;
}
