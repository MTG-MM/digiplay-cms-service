package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.dto.PackageCreateDto;
import com.wiinvent.gami.domain.dto.PackageUpdateDto;
import com.wiinvent.gami.domain.entities.Character;
import com.wiinvent.gami.domain.entities.Package;
import com.wiinvent.gami.domain.entities.PackageType;
import com.wiinvent.gami.domain.entities.type.CharacterCategoryType;
import com.wiinvent.gami.domain.entities.type.ProductType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.response.CharacterResponse;
import com.wiinvent.gami.domain.response.PackageResponse;
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
public class PackageService extends BaseService {
  @Autowired @Lazy PackageService self;
  public Page<PackageResponse> findAll(Integer id, Integer packageTypeId, Pageable pageable){
    Page<Package> packagePackage = packageStorage.findAll(id, packageTypeId, pageable);
    return modelMapper.toPagePackageResponse(packagePackage);
  }

  public PackageResponse getPackageDetail(int id) {
    Package productPackage = packageStorage.findById(id);
    if (productPackage == null) {
      throw new ResourceNotFoundException(Constants.PACKAGE_NOT_FOUND);
    }
    return modelMapper.toPackageResponse(productPackage);
  }

  public boolean createPackage(PackageCreateDto dto) {
    //validation
    if(Objects.isNull(dto.getStatus())) dto.setStatus(Status.ACTIVE);
    //map
    Package productPackage = modelMapper.toPackage(dto);
    //save
    try {
      self.save(productPackage);
    } catch (Exception e){
      log.error("==============>createPackage:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  public boolean updatePackage(Integer id, PackageUpdateDto dto) {
    //validation
    Package productPackage = packageStorage.findById(id);
    if (productPackage == null) {
      throw new BadRequestException(Constants.PACKAGE_NOT_FOUND);
    }
    //map
    modelMapper.mapPackageUpdateDtoToPackage(dto, productPackage);
    //save
    try {
      self.save(productPackage);
    } catch (Exception e){
      log.error("==============>updatePackage:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  public boolean deletePackage(Integer id) {
    //validation
    Package productPackage = packageStorage.findById(id);
    if (Objects.isNull(productPackage)) {
      throw new BadRequestException(Constants.PACKAGE_NOT_FOUND);
    }
    //set
    productPackage.setStatus(Status.DELETE);
    productPackage.setUpdatedAt(DateUtils.getNowMillisAtUtc());
    //save
    try {
      self.save(productPackage);
    } catch (Exception e){
      log.error("==============>deletePackage:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
  public void save(Package productPackage){
    packageStorage.save(productPackage);
  }

  public List<PackageResponse> getPackagesActive(Integer typeId){
    List<Package> packages = packageStorage.findAllPackageActive(typeId);
    return modelMapper.toListPackageResponse(packages);
  }
}
