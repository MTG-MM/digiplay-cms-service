package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.dto.PackageTypeCreateDto;
import com.wiinvent.gami.domain.dto.PackageTypeUpdateDto;
import com.wiinvent.gami.domain.entities.PackageType;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.response.PackageTypeResponse;
import com.wiinvent.gami.domain.utils.Constants;
import com.wiinvent.gami.domain.utils.DateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Log4j2
public class PackageTypeService extends BaseService{
  @Autowired @Lazy private PackageTypeService self;
  public Page<PackageTypeResponse> findAll(Pageable pageable){
    Page<PackageType> packageTypes = packageTypeStorage.findAll(pageable);

    return modelMapper.toPagePackageTypeResponse(packageTypes);
  }

  public PackageTypeResponse findPackageTypeDetail(Integer id){
    PackageType packageType = packageTypeStorage.findById(id);
    if(Objects.isNull(packageType)) throw new ResourceNotFoundException(Constants.PACKAGE_TYPE_NOT_FOUND);

    return modelMapper.toPackageTypeResponse(packageType);
  }

  public Boolean createPackageType(PackageTypeCreateDto dto){
    //validation

    //map
    PackageType packageType = modelMapper.toPackageType(dto);
    //save
    try {
      self.save(packageType);
    }catch (Exception e){
      log.error("==============>createPackageType exception = {}", e.getMessage());
      return false;
    }
    return true;
  }

  public Boolean updatePackageType(PackageTypeUpdateDto dto){
    //validation
    PackageType packageType = packageTypeStorage.findById(dto.getId());
    if(Objects.isNull(packageType)) throw new ResourceNotFoundException(Constants.PACKAGE_TYPE_NOT_FOUND);
    //map
    modelMapper.mapPackageTypeUpdateDtoToPackageType(dto, packageType);
    packageType.setUpdatedAt(DateUtils.getNowMillisAtUtc());
    //save
    try {
      self.save(packageType);
    }catch (Exception e){
      log.error("==============>updatePackageType exception = {}", e.getMessage());
      return false;
    }
    return true;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
  public void save(PackageType packageType){
    packageTypeStorage.save(packageType);
  }

  public List<PackageTypeResponse> findAllPackageTypeActive(){
    List<PackageType> packageTypes = packageTypeStorage.findAllPackageTypeActive();
    return modelMapper.toListPackageTypeResponse(packageTypes);
  }
}