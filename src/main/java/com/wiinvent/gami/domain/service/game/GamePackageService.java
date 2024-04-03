package com.wiinvent.gami.domain.service.game;

import com.wiinvent.gami.domain.dto.GamePackageCreateDto;
import com.wiinvent.gami.domain.dto.GamePackageUpdateDto;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.response.GamePackageResponse;
import com.wiinvent.gami.domain.entities.game.GamePackage;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Constant;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Log4j2
public class GamePackageService extends BaseService {

  public GamePackageResponse getPackageDetail(int id) {
    GamePackage gamePackage = gamePackageStorage.findById(id);
    if (gamePackage == null) {
      throw new ResourceNotFoundException(Constant.GAME_PACKAGE_NOT_FOUND);
    }
    return modelMapper.toGamePackageResponse(gamePackage);
  }

  public void createGamePackage(GamePackageCreateDto dto) {
    GamePackage gamePackage = modelMapper.toGamePackage(dto);
    gamePackageStorage.save(gamePackage);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public void updateGamePackage(int id, GamePackageUpdateDto dto) {
    GamePackage gamePackage = gamePackageStorage.findById(id);
    if (gamePackage == null) {
      throw new BadRequestException(Constant.GAME_PACKAGE_NOT_FOUND);
    }
    modelMapper.mapGamePackageUpdateDtoToGamePackage(dto, gamePackage);
    gamePackageStorage.save(gamePackage);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public void deleteGamePackage(int id) {
    GamePackage gamePackage = gamePackageStorage.findByIdAndStatusNot(id, Status.DELETE);
    if (Objects.isNull(gamePackage)) {
      throw new BadRequestException(Constant.GAME_PACKAGE_NOT_FOUND);
    }
    gamePackage.setStatus(Status.DELETE);
    gamePackageStorage.save(gamePackage);
  }
}