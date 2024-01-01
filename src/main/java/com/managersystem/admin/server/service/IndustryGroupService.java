package com.managersystem.admin.server.service;

import com.managersystem.admin.handleRequest.controller.dto.ApplicationDto;
import com.managersystem.admin.handleRequest.controller.response.ApplicationResponse;
import com.managersystem.admin.server.entities.ApplicationEntity;
import com.managersystem.admin.server.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IndustryGroupService extends BaseService {

  public boolean addIndustryGroup(ApplicationDto dto) {
    // Convert the DTO to an entity if needed
    ApplicationEntity applicationEntity = modelMapper.toIndustryGroupEntity(dto);
    // Call the appropriate repository method to save the entity
    // Return true if the operation was successful, false otherwise
    return applicationStorage.save(applicationEntity) != null;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
  public boolean updateIndustryGroup(int id, ApplicationDto dto) {
    ApplicationEntity entity = applicationStorage.findById(id);
    modelMapper.toIndustryGroupEntity(dto, entity);
    return applicationStorage.save(entity) != null;
  }

  @Transactional
  public boolean deleteIndustryGroup(int id) {
    applicationStorage.deleteById(id);
    return true;
  }

  public ApplicationResponse getIndustryGroupById(int id) {
    return modelMapper.toIndustryGroupResponse(applicationStorage.findById(id));
  }

  public List<ApplicationResponse> searchIndustryGroupByName(String name) {
    List<ApplicationEntity> industryGroupEntities = applicationStorage.findByNameContaining(name);
    return modelMapper.toIndustryGroupResponses(industryGroupEntities);
  }

}
