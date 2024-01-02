package com.managersystem.admin.server.service;

import com.managersystem.admin.handleRequest.controller.dto.ApplicationDto;
import com.managersystem.admin.handleRequest.controller.response.ApplicationResponse;
import com.managersystem.admin.handleRequest.controller.response.base.PageResponse;
import com.managersystem.admin.server.entities.AccountEntity;
import com.managersystem.admin.server.entities.ApplicationEntity;
import com.managersystem.admin.server.exception.BadRequestException;
import com.managersystem.admin.server.exception.base.ErrorCode;
import com.managersystem.admin.server.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApplicationService extends BaseService {

  public boolean addIndustryGroup(String username, ApplicationDto dto) {
    AccountEntity account = accountStorage.findByUsername(username);
    if(account == null){
      throw new BadRequestException(ErrorCode.USER_NOT_FOUND);
    }
    ApplicationEntity applicationEntity = modelMapper.toApplicationEntity(dto);
    applicationEntity.setCreatedBy(account.getId());
    applicationEntity.setUpdatedBy(account.getId());
    return applicationStorage.save(applicationEntity) != null;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
  public boolean updateIndustryGroup(int id, ApplicationDto dto) {
    ApplicationEntity entity = applicationStorage.findById(id);
    modelMapper.toApplicationEntity(dto, entity);
    return applicationStorage.save(entity) != null;
  }

  @Transactional
  public boolean deleteIndustryGroup(int id) {
    applicationStorage.deleteById(id);
    return true;
  }

  public ApplicationResponse getIndustryGroupById(int id) {
    return modelMapper.toApplicationResponse(applicationStorage.findById(id));
  }

  public List<ApplicationResponse> searchApplicationByName(String name, Pageable pageable) {
    List<ApplicationEntity> industryGroupEntities = applicationStorage.findByNameContaining(name, pageable);
    return modelMapper.toApplicationResponses(industryGroupEntities);
  }

  public PageResponse<ApplicationResponse> getPageApplication(Pageable pageable) {
    Page<ApplicationEntity> applications = applicationStorage.findAll(pageable);
    return PageResponse.createFrom(modelMapper.toPageApplicationResponse(applications));
  }
}
