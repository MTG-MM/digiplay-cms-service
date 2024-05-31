package com.wiinvent.gami.domain.service.user.transaction;

import com.wiinvent.gami.domain.entities.Task;
import com.wiinvent.gami.domain.entities.transaction.TaskUser;
import com.wiinvent.gami.domain.response.TaskUserResponse;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import com.wiinvent.gami.domain.response.type.CursorType;
import com.wiinvent.gami.domain.service.BaseService;
import com.wiinvent.gami.domain.utils.Helper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TaskUserService extends BaseService {
  public PageCursorResponse<TaskUserResponse> getTaskUser
      (UUID userId, UUID transId, LocalDate startDate, LocalDate endDate, Long next, Long pre, int limit) {
    Long startDateLong = null;
    Long endDateLong = null;
    if (Objects.nonNull(startDate)) startDateLong = Helper.startOfDaytoLong(startDate);
    if (Objects.nonNull(endDate)) endDateLong = Helper.endOfDaytoLong(endDate);
    List<TaskUser> taskUsers = new ArrayList<>();
    CursorType type = CursorType.FIRST;
    if (next == null && pre == null) {
      next = Helper.getNowMillisAtUtc();
      pre = 0L;
      taskUsers = taskUserStorage.findAll(userId, transId, startDateLong, endDateLong, next, pre, limit, type);
    } else if (next == null) {
      type = CursorType.NEXT;
      pre = 0L;
      taskUsers = taskUserStorage.findAll(userId, transId, startDateLong, endDateLong, next, pre, limit, type);
    } else if (pre == null) {
      type = CursorType.PRE;
      next = Helper.getNowMillisAtUtc();
      taskUsers = taskUserStorage.findAll(userId, transId, startDateLong, endDateLong, next, pre, limit, type);
    }
    List<Task> tasks = taskStorage.findAllByIdIn(taskUsers.stream().map(TaskUser::getTaskId).toList());
    Map<Integer, Task> taskMap = tasks.stream().collect(Collectors.toMap(Task::getId, Function.identity()));
    List<TaskUserResponse> responses = modelMapper.toTaskUserResponse(taskUsers);
    responses.forEach(r -> {
      Task task = taskMap.get(r.getTaskId());
      r.setTaskName(task != null ? task.getName() : null);
    });
    return new PageCursorResponse<>(responses, limit, type, "createdAt");
  }
}
