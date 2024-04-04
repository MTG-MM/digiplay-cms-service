package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.dto.GamePackageCreateDto;
import com.wiinvent.gami.domain.dto.GamePackageUpdateDto;
import com.wiinvent.gami.domain.dto.PackageCreateDto;
import com.wiinvent.gami.domain.dto.PackageUpdateDto;
import com.wiinvent.gami.domain.entities.Package;
import com.wiinvent.gami.domain.entities.game.GamePackage;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.response.GamePackageResponse;
import com.wiinvent.gami.domain.response.PackageResponse;
import com.wiinvent.gami.domain.utils.Constant;
import com.wiinvent.gami.domain.utils.JsonParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class PackageService extends BaseService {
  public PackageResponse getPackageDetail(int id) {
    Package aPackage = packageStorage.findById(id);
    if (aPackage == null) {
      throw new ResourceNotFoundException(Constant.GAME_PACKAGE_NOT_FOUND);
    }
    return modelMapper.toPackageResponse(aPackage);
  }

  public void createPackage(PackageCreateDto dto) {
    Package aPackage = modelMapper.toPackage(dto);
    packageStorage.save(aPackage);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public void updatePackage(int id, PackageUpdateDto dto) {
    Package aPackage = packageStorage.findById(id);
    if (aPackage == null) {
      throw new BadRequestException(Constant.GAME_PACKAGE_NOT_FOUND);
    }
    modelMapper.mapPackageUpdateDtoToPackage(dto, aPackage);
    packageStorage.save(aPackage);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public void deletePackage(int id) {
    Package aPackage = packageStorage.findByIdAndStatusNot(id, Status.DELETE);
    if (Objects.isNull(aPackage)) {
      throw new BadRequestException(Constant.GAME_PACKAGE_NOT_FOUND);
    }
    aPackage.setStatus(Status.DELETE);
    packageStorage.save(aPackage);
  }
}
