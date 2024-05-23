package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.dto.TaskCreateDto;
import com.wiinvent.gami.domain.dto.TaskUpdateDto;
import com.wiinvent.gami.domain.entities.type.DailyTaskType;
import com.wiinvent.gami.domain.response.TaskResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/portal/task")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class TaskController {
  @Autowired
  private TaskService taskService;
  @GetMapping("")
  public ResponseEntity<PageResponse<TaskResponse>> findAll(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) DailyTaskType type,
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      Pageable pageable
  ) {
    return ResponseEntity.ok(
        PageResponse.createFrom(taskService.findAll(name, type, pageable))
    );
  }

  @GetMapping("{id}")
  public ResponseEntity<TaskResponse> getTaskDetail(@PathVariable Integer id) {
    return ResponseEntity.ok(taskService.getTaskDetail(id));
  }

  @PostMapping("")
  public ResponseEntity<Boolean> createTask(@RequestBody @Valid TaskCreateDto taskCreateDto) {
    return ResponseEntity.ok(
        taskService.createTask(taskCreateDto)
    );
  }

  @PutMapping("{id}")
  public ResponseEntity<Boolean> updateTask(@PathVariable Integer id, @RequestBody @Valid TaskUpdateDto dto) {
    return ResponseEntity.ok(
        taskService.updateTask(id, dto)
    );
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Boolean> deleteTask(@PathVariable Integer id) {
    return ResponseEntity.ok(
        taskService.deleteTask(id)
    );
  }
}
