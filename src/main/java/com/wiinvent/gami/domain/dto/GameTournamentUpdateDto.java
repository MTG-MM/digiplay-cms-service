package com.wiinvent.gami.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserSegmentRewardItems;
import com.wiinvent.gami.domain.utils.DateUtils;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class GameTournamentUpdateDto {
  private String name;
  private Integer gameId;
  @Getter(AccessLevel.NONE)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime startAt;

  @Getter(AccessLevel.NONE)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime endAt;

  @Min(0)
  private Long duration;
  private List<UserSegmentRewardItems> rewardItems = new ArrayList<>();
  @NotNull
  private Status status;

  public Long getEndAt() {return DateUtils.timeToLongAtVn(endAt);}

  public Long getStartAt() {return DateUtils.timeToLongAtVn(startAt);}
}
