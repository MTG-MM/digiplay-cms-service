package com.wiinvent.gami.domain.service.user.transaction;

import com.wiinvent.gami.domain.entities.Character;
import com.wiinvent.gami.domain.entities.transaction.CharacterUserTransaction;
import com.wiinvent.gami.domain.entities.transaction.CoinTransaction;
import com.wiinvent.gami.domain.response.CharacterUserTransactionResponse;
import com.wiinvent.gami.domain.response.TransactionResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.response.type.CursorType;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Helper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CharacterUserTransactionService extends BaseService {
  public PageCursorResponse<CharacterUserTransactionResponse> getCharacterUserTransaction(UUID userId, Long next, Long pre, int limit) {
    CursorType type = CursorType.FIRST;
    List<CharacterUserTransaction> characterUserTransactions = new ArrayList<>();
    if (next == null && pre == null) {
      next = Helper.getNowMillisAtUtc();
      pre = 0L;
      characterUserTransactions = characterUserTransactionStorage.findAll(userId, next, pre, limit, type);
    } else if (pre == null){
      type = CursorType.NEXT;
      pre = 0L;
      characterUserTransactions = characterUserTransactionStorage.findAll(userId, next, pre, limit, type);
    } else if (next == null){
      type = CursorType.PRE;
      next = Helper.getNowMillisAtUtc();
      characterUserTransactions = characterUserTransactionStorage.findAll(userId, next, pre, limit, type);
      characterUserTransactions = characterUserTransactions.stream().sorted(Comparator.comparingLong(CharacterUserTransaction::getCreatedAt).reversed()).toList();
    }
    List<Character> characters = characterStorage.findAllByIdIn(characterUserTransactions.stream().map(CharacterUserTransaction::getCharacterId).toList());
    Map<Integer, Character> mapCharacter = characters.stream()
        .collect(Collectors.toMap(Character::getId, Function.identity()));
    List<CharacterUserTransactionResponse> responses = modelMapper.toListCharacterUserTransactionResponse(characterUserTransactions);
    responses.forEach(r ->
    {
      r.setName(mapCharacter.get(r.getCharacterId()).getName());
      r.setCoinPrice(mapCharacter.get(r.getCharacterId()).getCoinPrice());
      r.setPointPrice(mapCharacter.get(r.getCharacterId()).getPointPrice());
    });
    return new PageCursorResponse<>(responses, limit, type, "createdAt");
  }
}
