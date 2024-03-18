package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.app.controller.dto.ApplicationDto;
import com.wiinvent.gami.app.controller.response.ApplicationResponse;
import com.wiinvent.gami.app.controller.response.base.PageResponse;
import com.wiinvent.gami.domain.entities.Account;
import com.wiinvent.gami.domain.entities.Application;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.exception.base.ErrorCode;
import com.wiinvent.gami.domain.service.base.BaseService;
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
    Account account = accountStorage.findByUsername(username);
    if(account == null){
      throw new BadRequestException(ErrorCode.USER_NOT_FOUND);
    }
    Application application = modelMapper.toApplication(dto);
    return applicationStorage.save(application) != null;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
  public boolean updateIndustryGroup(int id, ApplicationDto dto) {
    Application entity = applicationStorage.findById(id);
    modelMapper.toApplication(dto, entity);
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
    List<Application> industryGroupEntities = applicationStorage.findByNameContaining(name, pageable);
    return modelMapper.toApplicationResponses(industryGroupEntities);
  }

  public PageResponse<ApplicationResponse> getPageApplication(Pageable pageable) {
    Page<Application> applications = applicationStorage.findAll(pageable);
    return PageResponse.createFrom(modelMapper.toPageApplicationResponse(applications));
  }
}
