package com.wiinvent.gami.domain.service.game;

import com.wiinvent.gami.domain.dto.GameCreateDto;
import com.wiinvent.gami.domain.dto.GameTypeCreateDto;
import com.wiinvent.gami.domain.dto.GameTypeUpdateDto;
import com.wiinvent.gami.domain.dto.GameUpdateDto;
import com.wiinvent.gami.domain.entities.game.GameType;
import com.wiinvent.gami.domain.entities.type.GameStatus;
import com.wiinvent.gami.domain.entities.type.GameTypeStatus;
import com.wiinvent.gami.domain.response.GameResponse;
import com.wiinvent.gami.domain.entities.game.Game;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.response.GameTypeResponse;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Constant;
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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Log4j2
public class GameService extends BaseService {
  @Autowired @Lazy private  GameService self;

  public GameResponse getGameDetail(Integer id) {
    Game game = gameStorage.findById(id);
    if (game == null) {
      throw new ResourceNotFoundException(Constant.GAME_NOT_FOUND);
    }
    return modelMapper.toGameResponse(game);
  }

  public Page<GameResponse> getAll(Integer id, String name, GameStatus status, Boolean isHot, Boolean isNew, Boolean isUpdate, Boolean isLock, Integer gameCategoryId, Integer gameTypeId, Pageable pageable) {
    Page<Game> games = gameStorage.findAll(id, name, status, isHot, isNew, isUpdate, isLock, gameCategoryId, gameTypeId, pageable);
    Page<GameResponse> responses = modelMapper.toPageGameResponse(games);
    return responses;
  }

  public boolean createGames(GameCreateDto createDto) {
    if(createDto.getStatus() == null) createDto.setStatus(GameStatus.ACTIVE);
    if(createDto.getIsHot() == null) createDto.setIsHot(false);

    Game game = modelMapper.toGame(createDto);
    try{
      self.save(game);
    }catch (Exception e){
      log.error("==============>createGames e = {}", e.getMessage());
      return false;
    }

    return true;
  }

  public boolean updateGame(Integer id, GameUpdateDto updateDto) {
    //validation
    Game game = gameStorage.findById(id);
    if (game == null) {
      throw new ResourceNotFoundException(Constant.GAME_NOT_FOUND);
    }
    //map
    modelMapper.mapGameUpdateDtoToGame(updateDto, game);
    //save
    try{
      self.save(game);
    }catch (Exception e){
      log.error("==============>updateGame:DB:Exception:{}", e.getMessage());
      return false;
    }
    //response
    return true;
  }

  public boolean deleteGame(Integer id){
    //validation
    Game game = gameStorage.findById(id);
    if (game == null) throw new ResourceNotFoundException(Constant.GAME_NOT_FOUND);
    //
    game.setStatus(GameStatus.DELETE);
    game.setUpdatedAt(DateUtils.getNowMillisAtUtc());
    //save
    try{
      self.save(game);
    }catch (Exception e){
      log.error("==============>deleteGame:DB:Exception:{}", e.getMessage());
      return false;
    }
    //response
    return true;
  }

  public List<GameStatus> findAllGameStatus(){
    return Game.getListStatusShow();
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public void save(Game game){
    gameStorage.save(game);
  }

  //================================================== GAME TYPE =================================
  //find all
  public Page<GameTypeResponse> findGameTypes(Integer id, String name, Pageable pageable){
    Page<GameType> gameTypes = gameTypeStorage.findGameTypes(id, name, pageable);
    return modelMapper.toPageGameTypeResponse(gameTypes);
  }
  //get detail
  public GameTypeResponse getGameTypeDetail(Integer id){
    GameType gameType = gameTypeStorage.findById(id);
    //validation
    if(Objects.isNull(gameType)) throw new ResourceNotFoundException(Constant.GAME_TYPE_NOT_FOUND);
    if(gameType.getStatus().equals(GameTypeStatus.DELETE)) throw new ResourceNotFoundException(Constant.GAME_TYPE_DELETED);

    return modelMapper.toGameTypeResponse(gameType);
  }
  //create
  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public boolean createGameType(GameTypeCreateDto dto){
    if(dto.getStatus() == null) dto.setStatus(GameTypeStatus.ACTIVE);

    GameType gameType = modelMapper.toGameType(dto);

    gameTypeStorage.save(gameType);
    return true;
  }
  //update
  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public boolean updateGameType(GameTypeUpdateDto dto){
    //check not found
    GameType gameType = gameTypeStorage.findById(dto.getId());
    //validation
    if(Objects.isNull(gameType)) throw new ResourceNotFoundException(Constant.GAME_TYPE_NOT_FOUND);
    if(gameType.getStatus().equals(GameTypeStatus.DELETE)) throw new ResourceNotFoundException(Constant.GAME_TYPE_DELETED);

    gameType.from(dto);
    gameType.setUpdatedAt(DateUtils.getNowMillisAtUtc());

    gameTypeStorage.save(gameType);
    return true;
  }
  //delete
  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public boolean deleteGameType(Integer id){
    //check not found
    GameType gameType = gameTypeStorage.findById(id);
    //validation
    if(Objects.isNull(gameType)) throw new ResourceNotFoundException(Constant.GAME_TYPE_NOT_FOUND);
    if(gameType.getStatus().equals(GameTypeStatus.DELETE)) throw new ResourceNotFoundException(Constant.GAME_TYPE_DELETED);

    gameType.setStatus(GameTypeStatus.DELETE);
    return true;
  }
}