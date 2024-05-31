package com.wiinvent.gami.domain.response;

import com.wiinvent.gami.domain.entities.type.QualifyRewardStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class TaskUserResponse {
  private UUID id;
  private Long completedAt;
  private Long createdAt;
  private Integer count;
  private Integer taskId;
  private String taskName;
  private QualifyRewardStatus status;
  private Integer score;
}
