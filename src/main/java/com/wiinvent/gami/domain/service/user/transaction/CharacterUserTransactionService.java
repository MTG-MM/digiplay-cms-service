package com.wiinvent.gami.domain.service.user.transaction;

import com.wiinvent.gami.domain.entities.Character;
import com.wiinvent.gami.domain.entities.transaction.CharacterUserTransaction;
import com.wiinvent.gami.domain.response.CharacterUserTransactionResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.response.type.CursorType;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Helper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CharacterUserTransactionService extends BaseService {
  public PageCursorResponse<CharacterUserTransactionResponse> getCharacterUserTransaction
      (UUID userId, UUID transId, LocalDate startDate, LocalDate endDate, Long next, Long pre, int limit) {
    Long startDateLong = null;
    Long endDateLong = null;
    if (Objects.nonNull(startDate)) startDateLong = Helper.startOfDaytoLong(startDate);
    if (Objects.nonNull(endDate)) endDateLong = Helper.endOfDaytoLong(endDate);
    CursorType type = CursorType.FIRST;
    List<CharacterUserTransaction> characterUserTransactions = new ArrayList<>();
    if (next == null && pre == null) {
      next = Helper.getNowMillisAtUtc();
      pre = 0L;
      characterUserTransactions = characterUserTransactionStorage.findAll(userId, transId, startDateLong, endDateLong, next, pre, limit, type);
    } else if (pre == null){
      type = CursorType.NEXT;
      pre = 0L;
      characterUserTransactions = characterUserTransactionStorage.findAll(userId, transId, startDateLong, endDateLong, next, pre, limit, type);
    } else if (next == null){
      type = CursorType.PRE;
      next = Helper.getNowMillisAtUtc();
      characterUserTransactions = characterUserTransactionStorage.findAll(userId, transId, startDateLong, endDateLong, next, pre, limit, type);
      characterUserTransactions = characterUserTransactions.stream().sorted(Comparator.comparingLong(CharacterUserTransaction::getCreatedAt).reversed()).toList();
    }
    List<Character> characters = characterStorage.findAllByIdIn(characterUserTransactions.stream().map(CharacterUserTransaction::getCharacterId).toList());
    Map<Integer, Character> mapCharacter = characters.stream()
        .collect(Collectors.toMap(Character::getId, Function.identity()));
    List<CharacterUserTransactionResponse> responses = modelMapper.toListCharacterUserTransactionResponse(characterUserTransactions);
    responses.forEach(r ->
    {
      Character character = mapCharacter.get(r.getCharacterId());
      r.setName(character != null ? character.getName() : null);
      r.setCoinPrice(character != null ? character.getCoinPrice() : null);
      r.setPointPrice(character != null ? character.getPointPrice() : null);
    });
    return new PageCursorResponse<>(responses, limit, type, "createdAt");
  }
}
