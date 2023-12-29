package com.managersystem.admin.server.service;

import com.managersystem.admin.server.stores.user.AccountStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseService {

  @Autowired protected AccountStorage accountStorage;
}
