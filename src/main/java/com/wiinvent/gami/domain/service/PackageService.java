package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.dto.GamePackageCreateDto;
import com.wiinvent.gami.domain.dto.GamePackageUpdateDto;
import com.wiinvent.gami.domain.dto.PackageCreateDto;
import com.wiinvent.gami.domain.dto.PackageUpdateDto;
import com.wiinvent.gami.domain.entities.Package;
import com.wiinvent.gami.domain.entities.game.GamePackage;
import com.wiinvent.gami.domain.entities.type.PackageType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.response.GamePackageResponse;
import com.wiinvent.gami.domain.response.PackageResponse;
import com.wiinvent.gami.domain.utils.Constant;
import com.wiinvent.gami.domain.utils.DateUtils;
import com.wiinvent.gami.domain.utils.JsonParser;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Log4j2
public class PackageService extends BaseService {
  @Autowired @Lazy PackageService self;
  public Page<PackageResponse> findAll(Integer id, PackageType type, Pageable pageable){
    Page<Package> packagePackage = packageStorage.findAll(id, type, pageable);
    return modelMapper.toPagePackageResponse(packagePackage);
  }

  public PackageResponse getPackageDetail(int id) {
    Package aPackage = packageStorage.findById(id);
    if (aPackage == null) {
      throw new ResourceNotFoundException(Constant.GAME_PACKAGE_NOT_FOUND);
    }
    return modelMapper.toPackageResponse(aPackage);
  }

  public boolean createPackage(PackageCreateDto dto) {
    //validation
    if(Objects.isNull(dto.getStatus())) dto.setStatus(Status.ACTIVE);
    //map
    Package aPackage = modelMapper.toPackage(dto);
    //save
    try {
      self.save(aPackage);
    } catch (Exception e){
      log.debug("==============>createPackage:DB:Exception:{}", e.getMessage());
      return false;
    }
    return true;
  }

  public boolean updatePackage(Integer id, PackageUpdateDto dto) {
    //validation
    Package aPackage = packageStorage.findById(id);
    if (aPackage == null) {
      throw new BadRequestException(Constant.GAME_PACKAGE_NOT_FOUND);
    }
    //map
    modelMapper.mapPackageUpdateDtoToPackage(dto, aPackage);
    //save
    try {
      self.save(aPackage);
    } catch (Exception e){
      log.debug("==============>updatePackage:DB:Exception:{}", e.getMessage());
      return false;
    }
    return true;
  }

  public boolean deletePackage(Integer id) {
    //validation
    Package aPackage = packageStorage.findById(id);
    if (Objects.isNull(aPackage)) {
      throw new BadRequestException(Constant.GAME_PACKAGE_NOT_FOUND);
    }
    //set
    aPackage.setStatus(Status.DELETE);
    aPackage.setUpdatedAt(DateUtils.getNowMillisAtUtc());
    //save
    try {
      self.save(aPackage);
    } catch (Exception e){
      log.debug("==============>deletePackage:DB:Exception:{}", e.getMessage());
      return false;
    }
    return true;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public void save(Package aPackage){
    packageStorage.save(aPackage);
  }

}
