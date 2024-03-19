package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.app.controller.dto.GameCreateDto;
import com.wiinvent.gami.app.controller.dto.GamePackageCreateDto;
import com.wiinvent.gami.app.controller.dto.GamePackageUpdateDto;
import com.wiinvent.gami.app.controller.dto.GameUpdateDto;
import com.wiinvent.gami.app.controller.response.GamePackageResponse;
import com.wiinvent.gami.app.controller.response.GameResponse;
import com.wiinvent.gami.domain.entities.Game;
import com.wiinvent.gami.domain.entities.GamePackage;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.service.base.BaseService;
import com.wiinvent.gami.domain.utils.Constant;
import com.wiinvent.gami.domain.utils.JsonParser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
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
    gamePackage.setPaymentMethodInfo(JsonParser.toJson(dto.getPaymentMethods()));
    gamePackageStorage.save(gamePackage);
  }

  public void updateGamePackage(int id, GamePackageUpdateDto dto) {
    GamePackage gamePackage = gamePackageStorage.findById(id);
    if (gamePackage == null) {
      throw new BadRequestException(Constant.GAME_PACKAGE_NOT_FOUND);
    }
    modelMapper.mapGamePackageUpdateDtoToGamePackage(dto, gamePackage);
    gamePackage.setPaymentMethodInfo(JsonParser.toJson(dto.getPaymentMethods()));
    gamePackageStorage.save(gamePackage);
  }
}