package com.managersystem.admin.server.stores;

import com.managersystem.admin.server.entities.ApplicationEntity;
import com.managersystem.admin.server.stores.base.BaseStorage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationStorage extends BaseStorage {


  public ApplicationEntity save(ApplicationEntity applicationEntity) {
    return applicationRepository.save(applicationEntity);
  }

  public void deleteById(int id) {
    applicationRepository.deleteById(id);
  }

  public ApplicationEntity findById(int id) {
    return applicationRepository.findById(id).orElse(null);
  }

  public List<ApplicationEntity> findByNameContaining(String name) {
    return applicationRepository.findByApplicationCodeContaining(name);
  }
}
