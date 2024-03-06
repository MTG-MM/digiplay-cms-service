package com.managersystem.admin.server.stores;

import com.managersystem.admin.server.entities.Application;
import com.managersystem.admin.server.stores.base.BaseStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationStorage extends BaseStorage {


  public Application save(Application application) {
    return applicationRepository.save(application);
  }

  public void deleteById(int id) {
    applicationRepository.deleteById(id);
  }

  public Application findById(int id) {
    return applicationRepository.findById(id).orElse(null);
  }

  public List<Application> findByNameContaining(String name, Pageable pageable) {
    return applicationRepository.findByCodeContaining(name, pageable);
  }

  public Page<Application> findAll(Pageable pageable) {
    return applicationRepository.findAllWithSorting(pageable);
  }
}
