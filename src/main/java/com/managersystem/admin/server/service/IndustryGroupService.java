package com.managersystem.admin.server.service;

import com.managersystem.admin.handleRequest.controller.dto.IndustryGroupDto;
import com.managersystem.admin.handleRequest.controller.response.IndustryGroupResponse;
import com.managersystem.admin.server.entities.IndustryGroupEntity;
import com.managersystem.admin.server.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IndustryGroupService extends BaseService {

  public boolean addIndustryGroup(IndustryGroupDto dto) {
    // Convert the DTO to an entity if needed
    IndustryGroupEntity industryGroupEntity = modelMapper.toIndustryGroupEntity(dto);
    // Call the appropriate repository method to save the entity
    // Return true if the operation was successful, false otherwise
    return industryGroupStorage.save(industryGroupEntity) != null;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
  public boolean updateIndustryGroup(int id, IndustryGroupDto dto) {
    IndustryGroupEntity entity = industryGroupStorage.findById(id);
    modelMapper.toIndustryGroupEntity(dto, entity);
    return industryGroupStorage.save(entity) != null;
  }

  @Transactional
  public boolean deleteIndustryGroup(int id) {
    industryGroupStorage.deleteById(id);
    return true;
  }

  public IndustryGroupResponse getIndustryGroupById(int id) {
    return modelMapper.toIndustryGroupResponse(industryGroupStorage.findById(id));
  }

  public List<IndustryGroupResponse> searchIndustryGroupByName(String name) {
    List<IndustryGroupEntity> industryGroupEntities = industryGroupStorage.findByNameContaining(name);
    return modelMapper.toIndustryGroupResponses(industryGroupEntities);
  }

}
