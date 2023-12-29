package com.managersystem.admin.server.stores;

import com.managersystem.admin.handleRequest.controller.response.IndustryGroupResponse;
import com.managersystem.admin.server.entities.AccountEntity;
import com.managersystem.admin.server.entities.IndustryGroupEntity;
import com.managersystem.admin.server.entities.type.UserRole;
import com.managersystem.admin.server.stores.base.BaseStorage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class IndustryGroupStorage extends BaseStorage {


  public IndustryGroupEntity save(IndustryGroupEntity industryGroupEntity) {
    return industryGroupRepository.save(industryGroupEntity);
  }

  public void deleteById(int id) {
    industryGroupRepository.deleteById(id);
  }

  public IndustryGroupEntity findById(int id) {
    return industryGroupRepository.findById(id).orElse(null);
  }

  public List<IndustryGroupEntity> findByNameContaining(String name) {
    return industryGroupRepository.findByIndustryGroupNameContaining(name);
  }
}
