package com.managersystem.admin.handleRequest.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.managersystem.admin.server.entities.RewardItem;
import com.managersystem.admin.server.entities.RewardSegmentDetail;
import com.managersystem.admin.server.entities.VoucherDetail;
import com.managersystem.admin.server.entities.type.RewardType;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardResponse {
  private Long itemId;
  private String itemName;
  private Long position;
  private Long value;
  private String imageUrl;
  private String code;
  private RewardType rewardType;
  @JsonIgnore
  private RewardItem rewardItem;
  @JsonIgnore
  private RewardSegmentDetail rewardSegmentDetail;

  public RewardResponse(RewardItem rewardItem, RewardSegmentDetail segmentDetail){
    itemId = rewardItem.getId();
    itemName = rewardItem.getRewardName();
    value = rewardItem.getValue();
    imageUrl = rewardItem.getImageUrl();
    rewardType = rewardItem.getRewardType();
    position = segmentDetail.getPosition();
    this.rewardItem = rewardItem;
    this.rewardSegmentDetail = segmentDetail;
  }

  public RewardResponse(RewardItem rewardItem, RewardSegmentDetail segmentDetail, VoucherDetail voucherDetail){
    itemId = rewardItem.getId();
    itemName = rewardItem.getRewardName();
    value = rewardItem.getValue();
    imageUrl = rewardItem.getImageUrl();
    rewardType = rewardItem.getRewardType();
    code = voucherDetail.getCode();
    position = segmentDetail.getPosition();
    this.rewardItem = rewardItem;
    this.rewardSegmentDetail = segmentDetail;
  }
}
