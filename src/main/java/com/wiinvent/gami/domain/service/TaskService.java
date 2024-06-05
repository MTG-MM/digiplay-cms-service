package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.dto.TaskCreateDto;
import com.wiinvent.gami.domain.dto.TaskUpdateDto;
import com.wiinvent.gami.domain.entities.Task;
import com.wiinvent.gami.domain.entities.type.DailyTaskType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.response.TaskResponse;
import com.wiinvent.gami.domain.utils.Constants;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
public class TaskService extends BaseService{
  @Autowired
  @Lazy
  TaskService self;

  public Page<TaskResponse> findAll(String name, DailyTaskType type, Pageable pageable){
    Page<Task> tasks = taskStorage.findAll(name, type, pageable);
    return modelMapper.toPageTaskResponse(tasks);
  }

  public TaskResponse getTaskDetail(Integer id){
    Task task = taskStorage.findById(id);
    if (task == null) {
      throw new BadRequestException(Constants.QUEST_NOT_FOUND);
    }
    return modelMapper.toTaskResponse(task);
  }

  public boolean createTask(TaskCreateDto dto) {
    Task task = modelMapper.toTask(dto);
    try {
      self.save(task);
    } catch (Exception e){
      log.error("==============>createQuest:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  public boolean updateTask(Integer id, TaskUpdateDto dto) {
    Task task = taskStorage.findById(id);
    if (task == null) {
      throw new BadRequestException(Constants.QUEST_NOT_FOUND);
    }
    modelMapper.mapTaskUpdateDtoToTask(dto, task);
    try {
      self.save(task);
    } catch (Exception e){
      log.error("==============>updateTask:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }


  public List<TaskResponse> getAllTask(){
    List<Task> tasks = taskStorage.findAllByStatus();
    return modelMapper.toListTaskResponse(tasks);
  }

  public boolean deleteTask(Integer id) {
    Task task = taskStorage.findById(id);
    if (task == null) {
      throw new BadRequestException(Constants.QUEST_NOT_FOUND);
    }
    task.setStatus(Status.DELETE);
    try {
      self.save(task);
    } catch (Exception e){
      log.error("==============>deleteQuest:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
  public void save(Task task){
    taskStorage.save(task);
  }
}
