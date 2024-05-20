package com.wiinvent.gami.domain.service.user.transaction;

import com.wiinvent.gami.domain.entities.Quest;
import com.wiinvent.gami.domain.entities.QuestTurn;
import com.wiinvent.gami.domain.entities.QuestUserHistory;
import com.wiinvent.gami.domain.entities.type.QuestStatusType;
import com.wiinvent.gami.domain.response.QuestUserHistoryResponse;
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
public class QuestUserHistoryService extends BaseService {
  public PageCursorResponse<QuestUserHistoryResponse> getQuestHistory
      (UUID userId, UUID transId, QuestStatusType questState, LocalDate startDate, LocalDate endDate, Long next, Long pre, int limit) {
    Long startDateLong = null;
    Long endDateLong = null;
    if (Objects.nonNull(startDate)) startDateLong = Helper.startOfDaytoLong(startDate);
    if (Objects.nonNull(endDate)) endDateLong = Helper.endOfDaytoLong(endDate);
    CursorType type = CursorType.FIRST;
    List<QuestUserHistory> questUserHistories = new ArrayList<>();
    if (next == null && pre == null) {
      next = Helper.getNowMillisAtUtc();
      pre = 0L;
      questUserHistories = questUserHistoryStorage.findAll(userId, transId, questState, startDateLong, endDateLong, next, pre, limit, type);
    } else if (pre == null){
      type = CursorType.NEXT;
      pre = 0L;
      questUserHistories = questUserHistoryStorage.findAll(userId, transId, questState, startDateLong, endDateLong, next, pre, limit, type);
    } else if (next == null){
      type = CursorType.PRE;
      next = Helper.getNowMillisAtUtc();
      questUserHistories = questUserHistoryStorage.findAll(userId, transId, questState, startDateLong, endDateLong, next, pre, limit, type);
      questUserHistories = questUserHistories.stream().sorted(Comparator.comparingLong(QuestUserHistory::getEndAt).reversed()).toList();
    }
    List<Quest> quests = questStorage.findAllByIdIn(questUserHistories.stream().map(QuestUserHistory::getQuestId).toList());
    List<QuestTurn> questTurns = questTurnStorage.findAllByIdIn(questUserHistories.stream().map(QuestUserHistory::getQuestTurnId).toList());
    Map<Long, Quest> questMap = quests.stream().collect(Collectors.toMap(Quest::getId, Function.identity()));
    Map<Long, QuestTurn> questTurnMap = questTurns.stream().collect(Collectors.toMap(QuestTurn::getId, Function.identity()));
    List<QuestUserHistoryResponse> responses = modelMapper.toListQuestUserHistoryResponse(questUserHistories);
    responses.forEach(r -> {
      Quest quest = questMap.getOrDefault(r.getQuestId(), null);
      QuestTurn questTurn = questTurnMap.getOrDefault(r.getQuestTurnId(), null);
      r.setQuestName(quest != null ? quest.getName() : null);
      r.setQuestTurnName(questTurn != null ? questTurn.getName() : null);
    });
    return new PageCursorResponse<>(responses, limit, type, "createdAt");
  }
}
