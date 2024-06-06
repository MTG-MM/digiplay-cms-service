package com.wiinvent.gami.domain.service.user.transaction;

import com.wiinvent.gami.domain.entities.Character;
import com.wiinvent.gami.domain.entities.transaction.CharacterUser;
import com.wiinvent.gami.domain.response.CharacterUserResponse;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Helper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CharacterUserService extends BaseService {
  public Page<CharacterUserResponse> getCharacterUsers
      (UUID userId, UUID transId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
    Long startDateLong = null;
    Long endDateLong = null;
    if (Objects.nonNull(startDate)) startDateLong = Helper.startOfDaytoLong(startDate);
    if (Objects.nonNull(endDate)) endDateLong = Helper.endOfDaytoLong(endDate);
    Page<CharacterUser> characterUsers = characterUserStorage.findAll(userId, transId, startDateLong, endDateLong, pageable);
    List<Integer> ids = characterUsers.stream().map(CharacterUser::getCharacterId).toList();
    Map<Integer, Character> characterMap = characterStorage.findAllByIdIn(ids).stream()
        .collect(Collectors.toMap(Character::getId, Function.identity()));

    List<CharacterUserResponse> characterUserResponses = modelMapper.toListCharacterUserResponse(characterUsers.toList());
    characterUserResponses.forEach(r -> {
      Character character = characterMap.getOrDefault(r.getCharacterId(), null);
      r.setCharacterName(character != null ? character.getName() : null);
    });
    return new PageImpl<>(characterUserResponses, pageable, characterUsers.getTotalElements());
  }
}
