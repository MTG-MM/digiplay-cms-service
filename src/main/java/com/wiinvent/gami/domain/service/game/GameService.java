package com.wiinvent.gami.domain.service.game;

import com.wiinvent.gami.domain.dto.GameCreateDto;
import com.wiinvent.gami.domain.dto.GameTypeCreateDto;
import com.wiinvent.gami.domain.dto.GameTypeUpdateDto;
import com.wiinvent.gami.domain.dto.GameUpdateDto;
import com.wiinvent.gami.domain.entities.game.GameType;
import com.wiinvent.gami.domain.entities.type.GameTypeStatus;
import com.wiinvent.gami.domain.response.GameResponse;
import com.wiinvent.gami.domain.entities.game.Game;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.response.GameTypeResponse;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Constant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService extends BaseService {
  public GameResponse getGameDetail(Integer id) {
    Game game = gameStorage.findById(id);
    if (game == null) {
      throw new ResourceNotFoundException(Constant.GAME_NOT_FOUND);
    }
    return modelMapper.toGameResponse(game);
  }

  public Page<GameResponse> getAll(Integer id, String name, Pageable pageable) {
    Page<Game> games = gameStorage.findAll(id, name, pageable);
    Page<GameResponse> responses = modelMapper.toPageGameResponse(games);
    return responses;
  }

  public void createGames(GameCreateDto createDto) {
    Game game = modelMapper.toGame(createDto);
    gameStorage.save(game);
  }

  public void updateGame(Integer id, GameUpdateDto updateDto) {
    Game game = gameStorage.findById(id);
    if (game == null) {
      throw new ResourceNotFoundException(Constant.GAME_NOT_FOUND);
    }
    modelMapper.mapGameUpdateDtoToGame(updateDto, game);
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
    gameType.from(dto);

    gameTypeStorage.save(gameType);
    return true;
  }
  //delete
  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public boolean deleteGameType(Integer id){
    //check not found
    GameType gameType = gameTypeStorage.findById(id);

    gameTypeStorage.deleteById(gameType.getId());
    return true;
  }
}